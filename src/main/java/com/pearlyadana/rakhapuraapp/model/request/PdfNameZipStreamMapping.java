package com.pearlyadana.rakhapuraapp.model.request;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class PdfNameZipStreamMapping implements Serializable {

    private static final long serialVersionUID = -7818224714690400072L;

    private String entryName;

    private ByteArrayOutputStream stream;

    public PdfNameZipStreamMapping() {
    }

    public PdfNameZipStreamMapping(String entryName, ByteArrayOutputStream stream) {
        this.entryName = entryName;
        this.stream = stream;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public ByteArrayOutputStream getStream() {
        return stream;
    }

    public void setStream(ByteArrayOutputStream stream) {
        this.stream = stream;
    }

}
