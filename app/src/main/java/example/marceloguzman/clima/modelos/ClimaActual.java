package example.marceloguzman.clima.modelos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import example.marceloguzman.clima.R;

/**
 * Created by MarceloGuzman on 26/09/15.
 */
public class ClimaActual {

    private double humedad;
    private double temperatura;
    private String localizacion;
    private long tiempo;
    private String icono;
    private double probabilidad;
    private String resumen;

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {

        temperatura = (temperatura - 32) / 1.8;

        this.temperatura = temperatura;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(double probabilidad) {
        this.probabilidad = probabilidad;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    //metodos para configurar las imagenes.

    public int getImagenID (){
        int idImagen = R.drawable.clear_day;


        switch(icono) {
            case "clear-day": idImagen = R.drawable.clear_day;break;
            case "clear-night": idImagen = R.drawable.clear_night;break;
            case "rain": idImagen = R.drawable.rain;break;
            case "snow": idImagen = R.drawable.snow;break;
            case "sleet": idImagen = R.drawable.sleet;break;
            case "wind": idImagen = R.drawable.wind;break;
            case "fog": idImagen = R.drawable.fog;break;
            case "cloudy": idImagen = R.drawable.cloudy;break;
            case "partly-cloudy-day":idImagen = R.drawable.snow;break;
            case "partly-cloudy-night":idImagen = R.drawable.snow;break;
        }

        return idImagen;
    }

    public String formatearFecha()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getLocalizacion()));

        Date fecha = new Date(getTiempo() * 1000);
        String tiempoString = simpleDateFormat.format(fecha);
        return tiempoString;

    }
}
