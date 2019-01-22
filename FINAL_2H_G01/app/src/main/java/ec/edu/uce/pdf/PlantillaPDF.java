package ec.edu.uce.pdf;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PlantillaPDF {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph parrafo;
    private Font fTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLD);
    private Font fSubTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fTexto = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private Font fResaltado = new Font(Font.FontFamily.TIMES_ROMAN, 17, Font.BOLD, BaseColor.RED);

    public PlantillaPDF(Context context) {
        this.context = context;
    }

    public void openFile() {
        crearArchivo();
        try {
            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

        } catch (Exception e) {
            Log.e("openFile", e.toString());
        }
    }

    private void crearArchivo() {
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");
        if (!folder.exists())
            folder.mkdirs();
        pdfFile = new File(folder, "Plantilla.pdf");
    }

    public void closeFile() {
        document.close();
    }

    public void agregarMetadata(String titulo, String tema, String autor) {
        document.addTitle(titulo);
        document.addSubject(tema);
        document.addAuthor(autor);
    }

    public void agregarTitulos(String titulo, String subtitulo, String fecha) {
        try {
            parrafo = new Paragraph();
            agregarSubParrafo(new Paragraph(titulo, fTitulo));
            agregarSubParrafo(new Paragraph(subtitulo, fSubTitulo));
            agregarSubParrafo(new Paragraph("Generado: " + fecha, fResaltado));
            parrafo.setSpacingAfter(30);
            document.add(parrafo);
        } catch (Exception e) {
            Log.e("agregarTitulos", e.toString());
        }
    }

    private void agregarSubParrafo(Paragraph subParrafo) {
        subParrafo.setAlignment(Element.ALIGN_CENTER);
        parrafo.add(subParrafo);
    }

    public void agregarParrafo(String texto) {
        try {
            parrafo = new Paragraph(texto, fTexto);
            parrafo.setSpacingAfter(5);
            parrafo.setSpacingBefore(5);
            document.add(parrafo);
        } catch (Exception e) {
            Log.e("agregarParrafo", e.toString());
        }
    }

    public void crearTabla(String[]encabezados, ArrayList<String[]>clientes){
        try {
        parrafo = new Paragraph();
        parrafo.setFont(fTexto);
        PdfPTable pdfPTable = new PdfPTable(encabezados.length);
        pdfPTable.setWidthPercentage(100);
        PdfPCell pdfPCell;
        int iC = 0;
        while(iC<encabezados.length){
            pdfPCell = new PdfPCell(new Phrase(encabezados[iC++], fSubTitulo));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);
        }
        for(int i = 0 ; i < clientes.size();i++){

            String[]filas =clientes.get(i);
            for (int j = 0 ; j< clientes.size(); j++){
                pdfPCell = new PdfPCell(new Phrase(filas[j]));

                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(40);
                pdfPTable.addCell(pdfPCell);
            }
        }
        parrafo.add(pdfPTable);
        document.add(parrafo);
        } catch (Exception e) {
            Log.e("crearTabla", e.toString());
        }
    }
//    public void viewPDF(){
//        Intent intent = new Intent(context, VerPDFActivity.class);
//        intent.putExtra("path", pdfFile.getAbsolutePath());
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }

    public void appViewPDF(Activity activity){
        if (pdfFile.exists()){
            Uri uri = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            try{
                activity.startActivity(intent);
            }catch (ActivityNotFoundException e){
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                Toast.makeText(activity.getApplicationContext(), "No cuentas con una app para ver PDFs", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(activity.getApplicationContext(), "Archivo no encontrado", Toast.LENGTH_LONG).show();
        }
    }
}
