package example.marceloguzman.clima.controles;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.marceloguzman.clima.R;
import example.marceloguzman.clima.modelos.ClimaActual;
import example.marceloguzman.clima.modelos.VentanaAlerta;


public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private ClimaActual climaActual;

    //declaracion de variables que representa a controles en pantalla
    /*private TextView tvGrados;
    private TextView tvTiempo;
    private TextView tvLocalizacion;
    private TextView tvResumen;
    private TextView tvHumedadValor;
    private TextView tvProbalidadValor;
    private ImageView ivIcono;*/
    @Bind(R.id.tvGrados) TextView tvGrados;
    @Bind(R.id.tvTiempo) TextView tvTiempo;
    @Bind(R.id.tvLocalizacion) TextView tvLocalizacion;
    @Bind(R.id.tvResumen) TextView tvResumen;
    @Bind(R.id.tvHumedadValor) TextView tvHumedadValor;
    @Bind(R.id.tvProbabilidadValor) TextView tvProbalidadValor;
    @Bind(R.id.ivIcono) ImageView ivIcono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        String apiKey = "34a397c24e20bd4492cd3978f272d0f8";
        double latitud = 37.8267;
        double longitud =  -122.423;
        String forecast = "https://api.forecast.io/forecast/"+apiKey+"/"+latitud+"," +longitud;

        if(redDisponible())
        {
            final OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(forecast).build();


            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {

                        if (response.isSuccessful()) {
                            String jsonInformacion = response.body().string();
                            //Log.d(TAG, response.body().string());
                            climaActual = obtenerClimaActual(jsonInformacion);

                            //presentamos datos en pantalla
                            /*tvGrados = (TextView) findViewById(R.id.tvGrados);
                            tvTiempo = (TextView) findViewById(R.id.tvTiempo);
                            tvLocalizacion = (TextView) findViewById(R.id.tvLocalizacion);
                            tvHumedadValor = (TextView) findViewById(R.id.tvHumedadValor);
                            tvProbalidadValor = (TextView) findViewById(R.id.tvProbabilidadValor);
                            tvResumen = (TextView) findViewById(R.id.tvResumen);
                            ivIcono = (ImageView) findViewById(R.id.ivIcono);*/

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DecimalFormat df = new DecimalFormat("#.##");
                                    tvGrados.setText(String.valueOf(df.format(climaActual.getTemperatura())));
                                    tvTiempo.setText(climaActual.formatearFecha());
                                    tvLocalizacion.setText(climaActual.getLocalizacion());
                                    tvHumedadValor.setText(String.valueOf(climaActual.getHumedad()));
                                    tvProbalidadValor.setText(String.valueOf(climaActual.getProbabilidad()));
                                    Drawable imagen = getResources().getDrawable(climaActual.getImagenID());
                                    ivIcono.setImageDrawable(imagen);
                                    tvResumen.setText(climaActual.getResumen());
                                }
                            });

                        } else {
                            alertaErrorUsuario();
                        }

                    } catch (IOException ex) {
                        Log.e(TAG,ex.getMessage());
                    } catch (JSONException ex) {
                        Log.e(TAG,ex.getMessage());
                    }

                }
            });
        } else {
            Toast.makeText(this, "La red de telcel no está disponible",Toast.LENGTH_LONG).show();
        }
    }

    private ClimaActual obtenerClimaActual(String informacion) throws JSONException {
        JSONObject forecast = new JSONObject(informacion);
        String currently = forecast.getString("currently");
        JSONObject forecastCurrently = new JSONObject(currently);

        ClimaActual currentlyTime = new ClimaActual();

        currentlyTime.setHumedad(forecastCurrently.getDouble("humidity"));
        currentlyTime.setIcono(forecastCurrently.getString("summary"));
        currentlyTime.setLocalizacion(forecast.getString("timezone"));
        currentlyTime.setProbabilidad(forecastCurrently.getDouble("precipProbability"));
        currentlyTime.setResumen(forecastCurrently.getString("summary"));
        currentlyTime.setTemperatura(forecastCurrently.getDouble("temperature"));
        currentlyTime.setTiempo(forecastCurrently.getLong("time"));


        return currentlyTime;

    }


    private boolean redDisponible() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean estaDisponible = false;

        if (networkInfo != null && networkInfo.isConnected())
        {
            estaDisponible = true;
        }

        return estaDisponible;
    }

    private void alertaErrorUsuario() {
        VentanaAlerta ventanaAlerta = new VentanaAlerta();

        ventanaAlerta.show(getFragmentManager(),"error_ventana");
    }


}
