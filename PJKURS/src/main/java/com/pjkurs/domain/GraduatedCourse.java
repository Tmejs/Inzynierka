package com.pjkurs.domain;

import java.sql.Date;

public class GraduatedCourse extends Course{
    public String certificateFile;
    public Date endDate;

    public String getCertificateFile() {
        return certificateFile;
    }

    public void setCertificateFile(String certificateFile) {
        this.certificateFile = certificateFile;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
