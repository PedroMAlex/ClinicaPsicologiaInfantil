package br.com.clinicapsicologiainfantil.controller.commons.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static br.com.clinicapsicologiainfantil.model.Constants.*;

@Component
public class GerarPdf {

    public void criar(String nomeMedico, String nomePaciente,
                      String descricaoReceita, String caminhoDoPDF) {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTON);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminhoDoPDF));
            document.open();

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            Paragraph nomePacienteParagrafo = new Paragraph("Paciente: " + nomePaciente, FONT_NORMAL);
            nomePacienteParagrafo.setAlignment(Element.ALIGN_LEFT);
            document.add(nomePacienteParagrafo);

            document.add(Chunk.NEWLINE);

            Paragraph descricaoReceitaParagrafo = new Paragraph(descricaoReceita, FONT_NORMAL);
            descricaoReceitaParagrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(descricaoReceitaParagrafo);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            SimpleDateFormat dateFormat = new SimpleDateFormat(MASK_DATE);
            String dataAtual = dateFormat.format(new Date());
            Paragraph dataAtualParagrafo = new Paragraph(dataAtual, FONT_NORMAL);
            dataAtualParagrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(dataAtualParagrafo);

            document.add(Chunk.NEWLINE);

            Paragraph nomeMedicoParagrafo = new Paragraph(nomeMedico, FONT_BOLD);
            nomeMedicoParagrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(nomeMedicoParagrafo);

            Paragraph crmMedicoParagrafo = new Paragraph(nomeMedico, FONT_NORMAL);
            crmMedicoParagrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(crmMedicoParagrafo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }
}

