<?php
require("config.php");
$lat=$_REQUEST['lat'];
$lon=$_REQUEST['lon'];
if ( !is_numeric($lat) or !is_numeric($lon) ) die;
header('Content-Type: application/json');


$data = array();

$data["coordinates"] = array( "lat" => "$lat", "lon" => "$lon");
$data["source"] = "cuzk:ruian";


// budova
$query="
  select s.kod,
        CASE
          WHEN s.typ_kod = 1 THEN 'Číslo popisné'
          WHEN s.typ_kod = 2 THEN 'Číslo evidenční'
          WHEN s.typ_kod = 3 THEN 'bez č.p./č.e.'
          ELSE ''
        END cislo_typ,
        trim(both '{}' from s.cisla_domovni::text) cisla_domovni,
        b.cislo_orientacni_hodnota || coalesce(b.cislo_orientacni_pismeno, '') cislo_orientacni,
        s.pocet_podlazi, a.nazev, s.plati_od,
        b.adrp_psc psc, ul.nazev ulice, c.nazev cast_obce,
        ob.nazev obec, ok.nazev okres, vu.nazev kraj
  from rn_stavebni_objekt s
      left outer join osmtables.zpusob_vyuziti_objektu a on s.zpusob_vyuziti_kod = a.kod
      left outer join rn_adresni_misto b on b.stavobj_kod = s.kod
      left outer join rn_ulice ul on b.ulice_kod = ul.kod
      left outer join rn_cast_obce c on c.kod = s.cobce_kod
      left outer join rn_obec ob on ul.obec_kod = ob.kod
      left outer join rn_okres ok on ob.okres_kod = ok.kod
      left outer join rn_vusc vu on ok.vusc_kod = vu.kod
  where st_contains(s.hranice,st_transform(st_geomfromtext('POINT(".$lon." ".$lat.")',4326),900913))
  and not s.deleted
  limit 1;
";
$result=pg_query($CONNECT,$query);

if (pg_num_rows($result) > 0)
{
  $row = pg_fetch_array($result, 0);


  $data["stavebni_objekt"] =
    array( "ruian_id" => $row["kod"],
           "cislo_domovni" => $row["cisla_domovni"],
           "cislo_domovni_typ" => $row["cislo_typ"],
           "cislo_orientacni" => $row["cislo_orientacni"],
           "ulice" => $row["ulice"],
           "cast_obce" => $row["cast_obce"],
           "obec" => $row["obec"],
           "okres" => $row["okres"],
           "kraj" => $row["kraj"],
           "psc" => $row["psc"],
           "pocet_podlazi" => $row["pocet_podlazi"],
           "zpusob_vyuziti" => $row["nazev"],
           "plati_od" => $row["plati_od"]);
} else
    $data["stavebni_objekt"] = array();

// pozemek
$query="
  select s.id, a.nazev as druh_pozemku, b.nazev as zpusob_vyuziti, s.plati_od,
         ku.nazev katastralni_uzemi,
         ob.nazev obec, ok.nazev okres, vu.nazev kraj
  from rn_parcela s
      left outer join osmtables.druh_pozemku a on s.druh_pozemku_kod = a.kod
      left outer join osmtables.zpusob_vyuziti_pozemku b on s.zpusob_vyu_poz_kod = b.kod
      left outer join rn_katastralni_uzemi ku on s.katuz_kod = ku.kod
      left outer join rn_obec ob on ku.obec_kod = ob.kod
      left outer join rn_okres ok on ob.okres_kod = ok.kod
      left outer join rn_vusc vu on ok.vusc_kod = vu.kod
  where st_contains(s.hranice,st_transform(st_geomfromtext('POINT(".$lon." ".$lat.")',4326),900913))
  and not s.deleted
  limit 1;
";

$result=pg_query($CONNECT,$query);
$error= pg_last_error($CONNECT);
if (pg_num_rows($result) > 0)
{
  $row = pg_fetch_array($result, 0);

  $data["parcela"] =
    array( "ruian_id" => $row["id"],
           "druh_pozemku" => $row["druh_pozemku"],
           "zpusob_vyuziti" => $row["zpusob_vyuziti"],
           "plati_od" => $row["plati_od"],
           "katastralni_uzemi" => $row["katastralni_uzemi"],
           "obec" => $row["obec"],
           "okres" => $row["okres"],
           "kraj" => $row["kraj"]);
} else
{
//   echo "error: $error\n";
  $data["parcela"] = array();
}

echo json_encode($data);

?>
