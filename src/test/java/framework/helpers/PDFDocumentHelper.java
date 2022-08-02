package framework.helpers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public final class PDFDocumentHelper {

    private PDFDocumentHelper() {
    }

    public static String extractTextByPage(final File pdfFile, final int pageNumber) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new AssertionError(String.format("You do not have permission to extract text for '%s' document",
                    pdfFile.getPath()));
            }

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(pageNumber);
            stripper.setEndPage(pageNumber);

            return stripper.getText(document);
        } catch (IOException e) {
            throw new AssertionError("The text from the PDF document was not received"
                + " because of the error: \r\n" + e.getMessage());
        }
    }

    public static String extractText(final File pdfFile) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new AssertionError(String.format("You do not have permission to extract text for '%s' document",
                    pdfFile.getPath()));
            }

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            throw new AssertionError("The text from the PDF document was not received"
                + " because of the error: \r\n" + e.getMessage());
        }
    }
}