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

import org.openstreetmap.josm.data.coor.LatLon;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Locale;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class ruianRecord {

    private double m_coor_lat, m_coor_lon;
    private String m_source;
    private long   m_objekt_ruian_id;
    private String m_objekt_cislo_domovni;
    private String m_objekt_cislo_domovni_typ;
    private String m_objekt_cislo_orientacni;
    private int    m_objekt_podlazi;
    private int    m_objekt_byty;
    private String m_objekt_zpusob_vyuziti;
    private String m_objekt_dokonceni;
    private String m_objekt_plati_od;
    private String m_objekt_ulice;
    private String m_objekt_cast_obce;
    private String m_objekt_obec;
    private String m_objekt_okres;
    private String m_objekt_kraj;
    private String m_objekt_psc;
    private long   m_parcela_ruian_id;
    private String m_parcela_druh_pozemku;
    private String m_parcela_zpusob_vyuziti;
    private String m_parcela_plati_od;
    private String m_parcela_katastralni_uzemi;
    private String m_parcela_obec;
    private String m_parcela_okres;
    private String m_parcela_kraj;
    private long   m_ulice_ruian_id;
    private String m_ulice_jmeno;


    public ruianRecord () {
      init();
    }

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
      m_objekt_dokonceni = "";
      m_objekt_plati_od = "";
      m_objekt_ulice = "";
      m_objekt_cast_obce = "";
      m_objekt_obec = "";
      m_objekt_okres = "";
      m_objekt_kraj = "";
      m_objekt_psc = "";
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
          m_objekt_ulice = stavebniObjekt.getString("ulice");
        } catch (Exception e) {
        }

        try {
          m_objekt_cast_obce = stavebniObjekt.getString("cast_obce");
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
          m_objekt_plati_od = stavebniObjekt.getString("plati_od");
        } catch (Exception e) {
        }

        try {
          m_objekt_dokonceni = stavebniObjekt.getString("dokonceni");
        } catch (Exception e) {
        }
      } catch (Exception e) {
      }

      try {
        JSONObject parcela = obj.getJSONObject("parcela");

        try {
          m_parcela_ruian_id = parcela.getLong("ruian_id");
        } catch (Exception e) {
        }

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

    public String getTextCoordinates () {

      String r = "";
      DecimalFormatSymbols symbols = new DecimalFormatSymbols();
      symbols.setDecimalSeparator('.');
      symbols.setGroupingSeparator(' ');
      DecimalFormat df = new DecimalFormat("#.00000", symbols);

      r = "(" + df.format(m_coor_lat) + ", " +
                df.format(m_coor_lon) + ")";
      return r;
    }

    public String getHtml () {

      String r = "";

      if (m_objekt_ruian_id == 0 && m_parcela_ruian_id == 0)
        return r;

      r = "<html>";
      r += "<br/>";
      if (m_objekt_ruian_id > 0) {
        r += "<i><u>Informace o objektu</u></i><br/>";
        r += "<b>RUIAN id: </b><a href=http://vdp.cuzk.cz/vdp/ruian/stavebniobjekty/" + m_objekt_ruian_id +">" + m_objekt_ruian_id + "</a><br/>";
        if (m_objekt_podlazi > 0) r += "<b>Počet podlaží: </b>" + m_objekt_podlazi + "<br/>";
        if (m_objekt_byty > 0) r += "<b>Počet bytů: </b>" + m_objekt_byty + "<br/>";
        r += "<b>Způsob využití: </b>" + m_objekt_zpusob_vyuziti + "<br/>";
        r += "<b>Datum dokončení: </b>" + m_objekt_dokonceni + "<br/>";
        r += "<b>Platí od: </b>" + m_objekt_plati_od + "<br/>";
        r += "<br/>";
        if (m_objekt_cislo_domovni == null || m_objekt_cislo_domovni.isEmpty()) {
          r += "<b>Budova: </b>" + m_objekt_cislo_domovni_typ + "<br/>";
          r += "<b>Obec: </b>" + m_parcela_obec +"<br/>";
          r += "<b>Okres: </b>" + m_parcela_okres +"<br/>";
          r += "<b>Kraj: </b>" + m_parcela_kraj +"<br/>";
        } else {
          String x = "";
          if ( !m_objekt_cislo_orientacni.isEmpty()) {
            x = "/" + m_objekt_cislo_orientacni;
          }
          r += "<b>" + m_objekt_cislo_domovni_typ + ": </b>" + m_objekt_cislo_domovni + x + "<br/>";
          r += "<b>Ulice: </b>" + m_objekt_ulice + "<br/>";
          r += "<b>Část obce: </b>" + m_objekt_cast_obce + "<br/>";
          r += "<b>Obec: </b>" + m_objekt_obec + "<br/>";
          r += "<b>Okres: </b>" + m_objekt_okres + "<br/>";
          r += "<b>Kraj: </b>" + m_objekt_kraj + "<br/>";
          r += "<b>PSČ: </b>" + m_objekt_psc + "<br/>";
        }
        r += "<br/>";
      }
      if (m_parcela_ruian_id > 0) {
        r += "<i><u>Informace o pozemku</u></i><br/>";
        r += "<b>RUIAN id: </b><a href=http://vdp.cuzk.cz/vdp/ruian/parcely/" + m_parcela_ruian_id +">" + m_parcela_ruian_id + "</a><br/>";
        r += "<b>Druh pozemku: </b>" + m_parcela_druh_pozemku +"<br/>";
        if (m_parcela_zpusob_vyuziti != "") r += "<b>Způsob využití: </b>" + m_parcela_zpusob_vyuziti +"<br/>";
        r += "<b>Platí od: </b>" + m_parcela_plati_od +"<br/>";
        r += "<br/>";
        r += "<b>Katastrální území: </b>" + m_parcela_katastralni_uzemi +"<br/>";
        if ( m_objekt_ruian_id == 0) {
          r += "<b>Obec: </b>" + m_parcela_obec +"<br/>";
          r += "<b>Okres: </b>" + m_parcela_okres +"<br/>";
          r += "<b>Kraj: </b>" + m_parcela_kraj +"<br/>";
        }
        if ( m_ulice_ruian_id > 0) {
          r += "<br/>";
          r += "<i><u>Informace o ulici</u></i><br/>";
          r += "<b>RUIAN id: </b><a href=http://vdp.cuzk.cz/vdp/ruian/ulice/" + m_ulice_ruian_id +">" + m_ulice_ruian_id + "</a><br/>";
          r += "<b>Jméno: </b>" + m_ulice_jmeno +"<br/>";
        }
      }
      r += "<hr/>";
      r += "<center><i><small>Zdroj: <a href=\"http://www.ruian.cz/\">" + m_source + "</a></small></i></center>";
      r += "</html>";

      return r;
    }
}

public class ruianModule {

    private String m_text = "";
    private String URL = "http://poloha.net/~marian/index.php";
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
     * Return coordinates text representation
     * @return String coordinatesText
     */
    public String getTextCoordinates() {

      return m_record.getTextCoordinates();
    }

    /**
     * Trace building on position.
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
