package example.marceloguzman.clima.modelos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import example.marceloguzman.clima.R;

/**
 * Created by MarceloGuzman on 26/09/15.
 */
public class VentanaAlerta extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getString(R.string.titulo_oops_ventana))
                .setMessage(context.getString(R.string.mensaje_ventana))
                .setPositiveButton(context.getString(R.string.mensaje_boton), null);

        AlertDialog dialog = builder.create();

        return dialog;
    }
}
