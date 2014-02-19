/**
 *  PointInfo - plugin for JOSM
 *  Marian Kyral
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.openstreetmap.josm.plugins.pointinfo;

import static org.openstreetmap.josm.tools.I18n.tr;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.tools.Utils;
import org.openstreetmap.josm.gui.Notification;
// import org.openstreetmap.josm.actions.PasteTagsAction;
import org.openstreetmap.josm.command.AddCommand;
import org.openstreetmap.josm.command.Command;
import org.openstreetmap.josm.command.SequenceCommand;
import org.openstreetmap.josm.data.osm.Node;

import org.openstreetmap.josm.data.osm.Tag;
import org.openstreetmap.josm.data.osm.TagCollection;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;
import java.lang.StringBuilder;

/**
 * Private class to store address places
 *
 */

class addrPlaces {
    private long    m_ruian_id;
    private String  m_cislo_domovni;
    private String  m_cislo_orientacni;
    private String  m_ulice;

    public addrPlaces () {
      init();
    }

    private void init () {
      m_ruian_id = 0;
      m_cislo_domovni = "";
      m_cislo_orientacni = "";
      m_ulice = "";
    }

    public void setRuianID (long v) {
      m_ruian_id = v;
    }

    public void setCisloDomovni (String v) {
      m_cislo_domovni = v;
    }

    public void setCisloOrientacni (String v) {
      m_cislo_orientacni = v;
    }

    public void setUlice (String v) {
      m_ulice = v;
    }

    public long getRuianID () {
      return m_ruian_id;
    }

    public String getCisloDomovni () {
      return m_cislo_domovni;
    }

    public String getCisloOrientacni () {
      return m_cislo_orientacni;
    }

    public String getUlice () {
      return m_ulice;
    }

}

/**
 * Private class contains RUIAN data
 *
 */

class ruianRecord {

    private double   m_coor_lat, m_coor_lon;
    private String   m_source;
    private long     m_objekt_ruian_id;
    private String   m_objekt_cislo_domovni;
    private String   m_objekt_cislo_domovni_typ;
    private String   m_objekt_cislo_orientacni;
    private int      m_objekt_podlazi;
    private int      m_objekt_byty;
    private String   m_objekt_zpusob_vyuziti;
    private String   m_objekt_zpusob_vyuziti_key;
    private String   m_objekt_zpusob_vyuziti_val;
    private String   m_objekt_dokonceni;
    private String   m_objekt_plati_od;
    private long     m_objekt_adr_id;
    private String   m_objekt_ulice;
    private String   m_objekt_cast_obce;
    private String   m_objekt_mestska_cast;
    private String   m_objekt_obec;
    private String   m_objekt_okres;
    private String   m_objekt_kraj;
    private String   m_objekt_psc;
    private long     m_parcela_ruian_id;
    private String   m_parcela_druh_pozemku;
    private String   m_parcela_zpusob_vyuziti;
    private String   m_parcela_plati_od;
    private String   m_parcela_katastralni_uzemi;
    private String   m_parcela_obec;
    private String   m_parcela_okres;
    private String   m_parcela_kraj;
    private long     m_ulice_ruian_id;
    private String   m_ulice_jmeno;

    private ArrayList <addrPlaces> m_adresni_mista;

    /**
    * Constructor
    *
    */
    public ruianRecord () {
      init();
    }

    /**
    * Initialization
    *
    */
    private void init () {

      m_coor_lat = 0;
      m_coor_lon = 0;
      m_source = "";
      m_objekt_ruian_id = 0;
      m_objekt_cislo_domovni = "";
      m_objekt_cislo_domovni_typ = "";
      m_objekt_cislo_orientacni = "";
      m_objekt_podlazi = 0;
      m_objekt_byty = 0;
      m_objekt_zpusob_vyuziti = "";
      m_objekt_zpusob_vyuziti_key = "";
      m_objekt_zpusob_vyuziti_val = "";
      m_objekt_dokonceni = "";
      m_objekt_plati_od = "";
      m_objekt_adr_id = 0;
      m_objekt_ulice = "";
      m_objekt_cast_obce = "";
      m_objekt_mestska_cast = "";
      m_objekt_obec = "";
      m_objekt_okres = "";
      m_objekt_kraj = "";
      m_objekt_psc = "";
      m_adresni_mista = new ArrayList<addrPlaces> ();
      m_parcela_ruian_id = 0;
      m_parcela_druh_pozemku = "";
      m_parcela_zpusob_vyuziti = "";
      m_parcela_plati_od = "";
      m_parcela_katastralni_uzemi = "";
      m_parcela_obec = "";
      m_parcela_okres = "";
      m_parcela_kraj = "";
      m_ulice_ruian_id = 0;
      m_ulice_jmeno = "";

    }

    /**
    * Parse given JSON string and fill variables with RUIAN data
    *
    */
    public void parseJSON (String jsonStr) {


      init();


    try {
      JSONObject obj = new JSONObject(jsonStr);

      try {
        m_coor_lat = obj.getJSONObject("coordinates").getDouble("lat");
      } catch (Exception e) {
      }

      try {
        m_coor_lon = obj.getJSONObject("coordinates").getDouble("lon");
      } catch (Exception e) {
      }

      try {
        m_source = obj.getString("source");
      } catch (Exception e) {
      }

// =========================================================================
      try {
        JSONObject stavebniObjekt = obj.getJSONObject("stavebni_objekt");

        try {
          m_objekt_ruian_id = stavebniObjekt.getLong("ruian_id");
        } catch (Exception e) {
        }

        try {
          m_objekt_cislo_domovni = stavebniObjekt.getString("cislo_domovni");
        } catch (Exception e) {
        }

        try {
          m_objekt_cislo_domovni_typ = stavebniObjekt.getString("cislo_domovni_typ");
        } catch (Exception e) {
        }

        try {
          m_objekt_cislo_orientacni = stavebniObjekt.getString("cislo_orientacni");
        } catch (Exception e) {
        }

        try {
          m_objekt_adr_id = stavebniObjekt.getLong("adresni_misto_kod");
        } catch (Exception e) {
        }

        try {
          m_objekt_ulice = stavebniObjekt.getString("ulice");
        } catch (Exception e) {
        }

        try {
          m_objekt_cast_obce = stavebniObjekt.getString("cast_obce");
        } catch (Exception e) {
        }

        try {
          m_objekt_mestska_cast = stavebniObjekt.getString("mestska_cast");
        } catch (Exception e) {
        }

        try {
          m_objekt_obec = stavebniObjekt.getString("obec");
        } catch (Exception e) {
        }

        try {
          m_objekt_okres = stavebniObjekt.getString("okres");
        } catch (Exception e) {
        }

        try {
          m_objekt_kraj = stavebniObjekt.getString("kraj");
        } catch (Exception e) {
        }

        try {
          m_objekt_psc = stavebniObjekt.getString("psc");
        } catch (Exception e) {
        }

        try {
          m_objekt_podlazi = stavebniObjekt.getInt("pocet_podlazi");
        } catch (Exception e) {
        }

        try {
          m_objekt_byty = stavebniObjekt.getInt("pocet_bytu");
        } catch (Exception e) {
        }

        try {
          m_objekt_zpusob_vyuziti = stavebniObjekt.getString("zpusob_vyuziti");
        } catch (Exception e) {
        }

        try {
          m_objekt_zpusob_vyuziti_key = stavebniObjekt.getString("zpusob_vyuziti_key");
        } catch (Exception e) {
        }

        try {
          m_objekt_zpusob_vyuziti_val = stavebniObjekt.getString("zpusob_vyuziti_val");
        } catch (Exception e) {
        }

        try {
          m_objekt_plati_od = stavebniObjekt.getString("plati_od");
        } catch (Exception e) {
        }

        try {
          m_objekt_dokonceni = stavebniObjekt.getString("dokonceni");
        } catch (Exception e) {
        }
      } catch (Exception e) {
      }

// =========================================================================
      try {
        JSONArray arr = obj.getJSONArray("adresni_mista");

        for(int i = 0; i < arr.length(); i++)
        {
          JSONObject adresniMisto = arr.getJSONObject(i);
          addrPlaces am = new addrPlaces();

          try {
            am.setRuianID(adresniMisto.getLong("ruian_id"));
          } catch (Exception e) {
          }

          try {
            am.setCisloDomovni(adresniMisto.getString("cislo_domovni"));
          } catch (Exception e) {
          }

          try {
            am.setCisloOrientacni(adresniMisto.getString("cislo_orientacni"));
          } catch (Exception e) {
          }

          try {
            am.setUlice(adresniMisto.getString("ulice"));
          } catch (Exception e) {
          }

          m_adresni_mista.add(am);
        }
      } catch (Exception e) {
      }

// =========================================================================
      try {
        JSONObject parcela = obj.getJSONObject("parcela");

        try {
          m_parcela_ruian_id = parcela.getLong("ruian_id");
        } catch (Exception e) {
        }
;
        try {
          m_parcela_druh_pozemku = parcela.getString("druh_pozemku");
        } catch (Exception e) {
        }

        try {
          m_parcela_zpusob_vyuziti = parcela.getString("zpusob_vyuziti");
        } catch (Exception e) {
        }

        try {
          m_parcela_plati_od = parcela.getString("plati_od");
        } catch (Exception e) {
        }

        try {
          m_parcela_katastralni_uzemi = parcela.getString("katastralni_uzemi");
        } catch (Exception e) {
        }

        try {
          m_parcela_obec = parcela.getString("obec");
        } catch (Exception e) {
        }

        try {
          m_parcela_okres = parcela.getString("okres");
        } catch (Exception e) {
        }

        try {
          m_parcela_kraj = parcela.getString("kraj");
        } catch (Exception e) {
        }

      } catch (Exception e) {
      }

// =========================================================================
      try {
        JSONObject ulice = obj.getJSONObject("ulice");

        try {
          m_ulice_ruian_id = ulice.getLong("ruian_id");
        } catch (Exception e) {
        }

        try {
          m_ulice_jmeno = ulice.getString("jmeno");
        } catch (Exception e) {
        }

      } catch (Exception e) {
      }

    } catch (Exception e) {
    }

    }

    /**
     * Return Html text representation
     * @return String htmlText
     */
    public String getHtml () {

      StringBuilder r = new StringBuilder();

      if (m_objekt_ruian_id == 0 &&
          m_parcela_ruian_id == 0 &&
          m_adresni_mista.size() == 0 &&
          m_ulice_ruian_id == 0 )
        return "";

      r.append("<html>");
      r.append("<br/>");
      if (m_objekt_ruian_id > 0) {
        r.append("<i><u>Informace o objektu</u></i>");
        r.append("&nbsp;&nbsp;<a href=file://tags.copy/building><img src="+getClass().getResource("/images/dialogs/copy-tags.png")+" border=0 alt=\"Vložit tagy do schránky\" ></a><br/>");
        r.append("<b>RUIAN id: </b><a href=http://vdp.cuzk.cz/vdp/ruian/stavebniobjekty/" + m_objekt_ruian_id +">" + m_objekt_ruian_id + "</a><br/>");
        if (m_objekt_podlazi > 0) r.append("<b>Počet podlaží: </b>" + m_objekt_podlazi + "<br/>");
        if (m_objekt_byty > 0) r.append("<b>Počet bytů: </b>" + m_objekt_byty + "<br/>");
        r.append("<b>Způsob využití: </b>" + m_objekt_zpusob_vyuziti + "<br/>");
        r.append("<b>Datum dokončení: </b>" + m_objekt_dokonceni + "<br/>");
        r.append("<b>Platí od: </b>" + m_objekt_plati_od + "<br/>");
        r.append("<br/>");
        r.append("<i><u>Informace o adrese</u></i><br/>");
        if (m_adresni_mista.size() > 0) {
          r.append("<b>" + m_objekt_cislo_domovni_typ + "</b> (více adres)<b>: </b>" + m_objekt_cislo_domovni + "<br/>");
          r.append("<b>Část obce: </b>" + m_objekt_cast_obce + "<br/>");
          if (m_objekt_mestska_cast.length() > 0) r.append("<b>Městská část: </b>" + m_objekt_mestska_cast + "<br/>");
          r.append("<b>Obec: </b>" + m_parcela_obec +"<br/>");
          r.append("<b>Okres: </b>" + m_parcela_okres +"<br/>");
          r.append("<b>Kraj: </b>" + m_parcela_kraj +"<br/>");
        } else if (m_objekt_cislo_domovni == null || m_objekt_cislo_domovni.isEmpty()) {
          r.append("<b>Budova: </b>" + m_objekt_cislo_domovni_typ + "<br/>");
          if (m_objekt_mestska_cast.length() > 0) r.append("<b>Městská část: </b>" + m_objekt_mestska_cast + "<br/>");
          r.append("<b>Obec: </b>" + m_parcela_obec +"<br/>");
          r.append("<b>Okres: </b>" + m_parcela_okres +"<br/>");
          r.append("<b>Kraj: </b>" + m_parcela_kraj +"<br/>");
        } else {
          String x = "";
          String x_name = "";
          if ( !m_objekt_cislo_orientacni.isEmpty()) {
            x = "/" + m_objekt_cislo_orientacni;
            x_name = "/orientační";
          }
          r.append("<b>RUIAN id: </b><a href=http://vdp.cuzk.cz/vdp/ruian/adresnimista/" + m_objekt_adr_id +">" + m_objekt_adr_id + "</a><br/>");
          r.append("<b>" + m_objekt_cislo_domovni_typ + x_name + ": </b>" + m_objekt_cislo_domovni + x);
          r.append("&nbsp;&nbsp;<a href=file://tags.copy/address:0><img src="+getClass().getResource("/images/dialogs/copy-tags.png")+" border=0 alt=\"Vložit tagy do schránky\"></a>");
          r.append("&nbsp;&nbsp;<a href=file://tags.create/address:0><img src="+getClass().getResource("/images/dialogs/create-addr.png")+" border=0 alt=\"Vytvořit adresní bod\"></a>");
          r.append("<br/>");
          if (!m_objekt_ulice.isEmpty()) r.append("<b>Ulice: </b>" + m_objekt_ulice + "<br/>");
          r.append("<b>Část obce: </b>" + m_objekt_cast_obce + "<br/>");
          if (m_objekt_mestska_cast.length() > 0) r.append("<b>Městská část: </b>" + m_objekt_mestska_cast + "<br/>");
          r.append("<b>Obec: </b>" + m_objekt_obec + "<br/>");
          r.append("<b>Okres: </b>" + m_objekt_okres + "<br/>");
          r.append("<b>Kraj: </b>" + m_objekt_kraj + "<br/>");
          r.append("<b>PSČ: </b>" + m_objekt_psc + "<br/>");
        }
        r.append("<br/>");
      }
      if (m_adresni_mista.size() > 0) {
        String x = "";
        if (m_objekt_cislo_domovni_typ.equals("Číslo evidenční")) {
          x = "ev.";
        }
        r.append("<i><u>Adresní místa</u></i><br/>");
        for (int i=0; i<m_adresni_mista.size(); i++) {
          r.append("<a href=http://vdp.cuzk.cz/vdp/ruian/adresnimista/" + m_adresni_mista.get(i).getRuianID() + ">");
          r.append(m_adresni_mista.get(i).getRuianID() + "</a> ");
          r.append(m_adresni_mista.get(i).getUlice() + " " + x + m_adresni_mista.get(i).getCisloDomovni());
          if (!m_adresni_mista.get(i).getCisloOrientacni().isEmpty()) {
            r.append("/" + m_adresni_mista.get(i).getCisloOrientacni());
          }
          r.append("&nbsp;&nbsp;<a href=file://tags.copy/address:"+i+"><img src="+getClass().getResource("/images/dialogs/copy-tags.png")+" border=0 alt=\"Vložit tagy do schránky\"></a>");
          r.append("&nbsp;&nbsp;<a href=file://tags.create/address:"+i+"><img src="+getClass().getResource("/images/dialogs/create-addr.png")+" border=0 alt=\"Vytvořit adresní bod\"></a>");
          r.append("<br/>");
        }
        r.append("<br/>");
      }
      if (m_parcela_ruian_id > 0) {
        r.append("<i><u>Informace o pozemku</u></i>");
//         r.append("&nbsp;&nbsp;<a href=file://tags.copy/parcel><img src="+getClass().getResource("/images/dialogs/copy-tags.png")+" border=0 alt=\"Vložit tagy do schránky\"></a>");
        r.append("<br/>");
        r.append("<b>RUIAN id: </b><a href=http://vdp.cuzk.cz/vdp/ruian/parcely/" + m_parcela_ruian_id +">" + m_parcela_ruian_id + "</a><br/>");
        r.append("<b>Druh pozemku: </b>" + m_parcela_druh_pozemku +"<br/>");
        if (m_parcela_zpusob_vyuziti != "") r.append("<b>Způsob využití: </b>" + m_parcela_zpusob_vyuziti +"<br/>");
        r.append("<b>Platí od: </b>" + m_parcela_plati_od +"<br/>");
        r.append("<br/>");
        r.append("<b>Katastrální území: </b>" + m_parcela_katastralni_uzemi +"<br/>");
        if (m_objekt_ruian_id == 0) {
          r.append("<b>Obec: </b>" + m_parcela_obec +"<br/>");
          r.append("<b>Okres: </b>" + m_parcela_okres +"<br/>");
          r.append("<b>Kraj: </b>" + m_parcela_kraj +"<br/>");
        }
        r.append("<br/>");
      }
      if (m_ulice_ruian_id > 0) {
        r.append("<i><u>Informace o ulici</u></i>");
        r.append("&nbsp;&nbsp;<a href=file://tags.copy/street><img src="+getClass().getResource("/images/dialogs/copy-tags.png")+" border=0 alt=\"Vložit tagy do schránky\"></a><br/>");
        r.append("<b>RUIAN id: </b><a href=http://vdp.cuzk.cz/vdp/ruian/ulice/" + m_ulice_ruian_id +">" + m_ulice_ruian_id + "</a><br/>");
        r.append("<b>Jméno: </b>" + m_ulice_jmeno +"<br/>");
        r.append("<br/>");
      }
      r.append("<hr/>");
      r.append("<center><i><small>Zdroj: <a href=\"http://www.ruian.cz/\">" + m_source + "</a></small></i></center>");
      r.append("</html>");

      return r.toString();
    }

    /**
     * Construct tag string for clipboard
     * @param k OSM Key
     * @param v OSM Value
     * @return String OSM tag string for clipboard
     */
    String tagToString (String k, String v) {
      String r = "\"" + k + "\"=\"" + v + "\"\n";
      return r;
    }

    /**
     * Prepare OSM keys
     * @param keyType What to prepare (building, address, parcel. street)
     * @return String with OSM tags
     */
    String getKeys (String keyType) {
      StringBuilder c = new StringBuilder();

      // Copy building tags to clipboard
      if (keyType.equals("building") && m_objekt_ruian_id > 0) {
        c.append(tagToString("ref:ruian:building", Long.toString(m_objekt_ruian_id)));
        if (m_objekt_zpusob_vyuziti_key.length() > 0 &&
            m_objekt_zpusob_vyuziti_val.length() > 0
           ) {
          c.append(tagToString(m_objekt_zpusob_vyuziti_key, m_objekt_zpusob_vyuziti_val));
        }
        if (m_objekt_podlazi > 0) {
          c.append(tagToString("building:levels", Integer.toString(m_objekt_podlazi)));
        }
        if (m_objekt_byty > 0) {
          c.append(tagToString("building:flats", Integer.toString(m_objekt_byty)));
        }
        if (m_objekt_dokonceni.length() > 0) {
          c.append(tagToString("start_date", m_objekt_dokonceni));
        }
        c.append(tagToString("source", "cuzk:ruian"));
      }

      // Copy address tags to clipboard
      if (keyType.startsWith("address")) {
        if (m_adresni_mista.size() == 0) {
          // Only one address place
          if (! m_objekt_cislo_domovni_typ.equals("Číslo evidenční")) {
            // Cislo popisne
            c.append(tagToString("addr:conscriptionnumber", m_objekt_cislo_domovni));
          } else {
            // Cislo evidencni
            c.append(tagToString("addr:provisionalnumber", m_objekt_cislo_domovni));
          }

          // Cislo orientacni
          if (!m_objekt_cislo_orientacni.isEmpty()) {
            c.append(tagToString("addr:streetnumber", m_objekt_cislo_orientacni));
          }

          // Domovni cislo
          StringBuilder addr = new StringBuilder();
          if (! m_objekt_cislo_domovni_typ.equals("Číslo evidenční")) {
            addr.append(m_objekt_cislo_domovni);
          } else {
            addr.append("ev."+m_objekt_cislo_domovni);
          }
          if (!m_objekt_cislo_orientacni.isEmpty()) {
            addr.append("/" + m_objekt_cislo_orientacni);
          }
          c.append(tagToString("addr:housenumber", addr.toString()));

          // Street
          if (!m_objekt_ulice.isEmpty()) {
            c.append(tagToString("addr:street", m_objekt_ulice));
          }
          //RUIAN ID
          if (m_objekt_adr_id > 0) {
            c.append(tagToString("ref:ruian:addr", Long.toString(m_objekt_adr_id)));
          }
        } else {
          // More address places
          String[] key = keyType.split(":");
          int i = new Integer(key[1]);
          System.out.println("Address ID: " + i);

          if (! m_objekt_cislo_domovni_typ.equals("Číslo evidenční")) {
            // Cislo popisne
            c.append(tagToString("addr:conscriptionnumber", m_adresni_mista.get(i).getCisloDomovni()));
          } else {
            // Cislo evidencni
            c.append(tagToString("addr:provisionalnumber", m_adresni_mista.get(i).getCisloDomovni()));
          }

          // Cislo orientacni
          if (!m_adresni_mista.get(i).getCisloOrientacni().isEmpty()) {
            c.append(tagToString("addr:streetnumber", m_adresni_mista.get(i).getCisloOrientacni()));
          }

          // Domovni cislo
          StringBuilder addr = new StringBuilder();
          if (! m_objekt_cislo_domovni_typ.equals("Číslo evidenční")) {
            addr.append(m_adresni_mista.get(i).getCisloDomovni());
          } else {
            addr.append("ev." + m_adresni_mista.get(i).getCisloDomovni());
          }
          if (!m_adresni_mista.get(i).getCisloOrientacni().isEmpty()) {
            addr.append("/" + m_adresni_mista.get(i).getCisloOrientacni());
          }
          c.append(tagToString("addr:housenumber", addr.toString()));

          // Street
          if (!m_adresni_mista.get(i).getUlice().isEmpty()) {
            c.append(tagToString("addr:street", m_adresni_mista.get(i).getUlice()));
          }

          //RUIAN ID
            c.append(tagToString("ref:ruian:addr", Long.toString(m_adresni_mista.get(i).getRuianID())));
        }

        // Common address parts
        StringBuilder is_in = new StringBuilder();

        // Place
        if (!m_objekt_cast_obce.isEmpty()) {
          c.append(tagToString("addr:place", m_objekt_cast_obce));
          is_in.append(m_objekt_cast_obce + ", ");
        }

        if (!m_objekt_mestska_cast.isEmpty()) {
          c.append(tagToString("addr:suburb", m_objekt_mestska_cast));
          is_in.append(m_objekt_mestska_cast + ", ");
        }

        // City
        if (!m_objekt_obec.isEmpty()) {
          c.append(tagToString("addr:city", m_objekt_obec));
          if (!m_objekt_obec.equals(m_objekt_cast_obce)) {
            is_in.append(m_objekt_obec + ", ");
          }
        }

        // Postcode
        if (!m_objekt_psc.isEmpty()) {
          c.append(tagToString("addr:postcode", m_objekt_psc));
        }

        // District
        if (!m_objekt_okres.isEmpty()) {
          c.append(tagToString("addr:district", m_objekt_okres));
          is_in.append(m_objekt_okres + ", ");
        }

        // Region
        if (!m_objekt_kraj.isEmpty()) {
          is_in.append(m_objekt_kraj + ", ");
        }

        // Country
        c.append(tagToString("addr:country", "CZ"));
        is_in.append("CZ");

//         c.append(tagToString("is_in", is_in.toString()));

        // Source
        c.append(tagToString("source:addr", "cuzk:ruian"));
      }

      // Copy parcel tags to clipboard

      // Copy street tags to clipboard
      if (keyType.equals("street") && m_ulice_ruian_id > 0) {
        c.append(tagToString("ref:ruian:street", Long.toString(m_ulice_ruian_id)));
        c.append(tagToString("name", m_ulice_jmeno));
        c.append(tagToString("source", "cuzk:ruian"));
      }

      return c.toString();
    }

    /**
     * Create new address poing on current location with given tags
     * @param t OSM tags in string
     */
    void createAddrPoint (String t) {
      Collection<Command> commands = new LinkedList<Command>();
      Node node = new Node(new LatLon(m_coor_lat, m_coor_lon));
      commands.add(new AddCommand(node));

      Collection<OsmPrimitive> coll = new LinkedList<OsmPrimitive>();
      coll.add(node);

      TagCollection tc = new TagCollection();
      ArrayList <String> list = new ArrayList<String>(Arrays.asList(t.split("\n")));
      for (String line : list) {
        String[] tag = line.split("\"=\"");
        System.out.println("<" + tag[0] + ">. <" + tag[1] +">");
        tc.add(new Tag(tag[0].substring(1), tag[1].substring(0,tag[1].length()-1)));
      }

      tc.applyTo(coll);

      Main.main.undoRedo.add(new SequenceCommand(tr("Add new address point"), commands));
    }

    /**
     * Perform given action
     *  e.g.: copy tags to clipboard
     * @param act Action to be performed
     */
    public void performAction(String act) {

      System.out.println("act: " + act.substring(7));
      String[] params = act.substring(7).split("/");
      if (!params[0].equals("tags.copy") && !params[0].startsWith("tags.create")) {
        return;
      }

      String task = getKeys(params[1]);

      // Copy tags to clipboard
      if (params[0].equals("tags.copy")) {
        if (task.length() > 0) {
          Utils.copyToClipboard(task);
          PointInfoUtils.showNotification(tr("Tags copied to clipboard."), "info");
        }
      }

      // Create address node
      if (params[0].startsWith("tags.create")) {
        if (task.length() > 0) {
          createAddrPoint(task);
          PointInfoUtils.showNotification(tr("New address point added."), "info");
        }
      }
    }

}

/**
 * An module for the Czech RUIAN database
 *
 */
public class ruianModule {

    private String m_text = "";
    private String URL = "http://poloha.net/~marian/pointInfo/index.php";
    protected PointInfoServer server = new PointInfoServer();

    private ruianRecord m_record = new ruianRecord();

    public ruianModule() {

    }

    /**
     * Return Html text representation
     * @return String htmlText
     */
    public String getHtml() {

      return m_record.getHtml();
    }

    /**
     * Perform given action
     *  e.g.: copy tags to clipboard
     * @param act Action to be performed
     */
    public void performAction(String act) {

      m_record.performAction(act);
    }

    /**
     * Get a information about given position from RUIAN database.
     * @param pos Position on the map
     */
    public void prepareData(LatLon pos) {
        try {

             String request = URL + "?lat=" + pos.lat() + "&lon=" + pos.lon();
             System.out.println("Request: "+ request);
             String content = server.callServer(request);
             System.out.println("Reply: " + content);
             m_record.parseJSON(content);
        } catch (Exception e) {

        }
    }
}
