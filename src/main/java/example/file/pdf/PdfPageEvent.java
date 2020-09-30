package example.file.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PdfPageEvent extends PdfPageEventHelper {

    private PdfTemplate totalTemplate;
    private BaseFont baseFont;

    public PdfPageEvent(BaseFont baseFont) {
        this.baseFont = baseFont;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        totalTemplate = writer.getDirectContent().createTemplate(50, 50);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte content = writer.getDirectContent();
        int pageNum = writer.getPageNumber();

        for (int i = 1; i <= pageNum; i++) {
            Rectangle rt = new Rectangle(50 + i, 805, 80 + i, 830);
            rt.setBackgroundColor(BaseColor.BLACK);
            content.rectangle(rt);
        }

        Phrase footer = new Phrase(pageNum + " / ", new Font(baseFont, 12, Font.NORMAL));
        float len = baseFont.getWidthPoint(footer.getContent(), 12);
        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, footer,
                (document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F + 20F,
                document.bottom() - 20,
                0);

        content.addTemplate(totalTemplate, (document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F + 20F, document.bottom() - 20);
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        totalTemplate.beginText();
        totalTemplate.setFontAndSize(baseFont, 12);
        String totalPageNum = String.valueOf(writer.getPageNumber()-1);
        totalTemplate.showText(totalPageNum);
        totalTemplate.endText();
        totalTemplate.closePath();
    }
}
