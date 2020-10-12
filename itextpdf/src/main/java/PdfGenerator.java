import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 支绍昆 20191218
 */
public class PdfGenerator {

    public void of(String filePath) throws IOException, DocumentException {
        BaseFont baseFont = BaseFont.createFont("ttf/simfang.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont, 13);

        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        pdfWriter.setPageEvent(new PdfPageEvent(baseFont));
        document.open();

        Phrase title = new Phrase("第一单元语文考试"+"\n",new Font(baseFont, 15, Font.BOLD));
        document.add(title);
        Phrase p = new Phrase("本次试卷满分为：100分\n\n", font);
        document.add(p);

        Paragraph type = new Paragraph();
        type.setFont(new Font(baseFont, 13, Font.BOLD));
        type.setSpacingAfter(10F);
        type.add("一、单选题（共10题 10分）\n");
        document.add(type);

        Paragraph item = new Paragraph();
        item.setFont(font);
        item.setSpacingAfter(100F);
        item.add("1、（1分）");
        item.add(" 鲸鱼是鱼吗？");
        item.add(" A.是 B.不是 C.其它");
        document.add(item);

        document.close();
    }

    public static void main(String[] args) throws IOException, DocumentException {
        PdfGenerator p = new PdfGenerator();
        p.of("H:\\temp\\test.pdf");
    }
}
