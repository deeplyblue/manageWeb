package com.oriental.manage.core.fileUtils;

import lombok.Data;
import org.webbuilder.office.excel.config.Header;

/**
 * Created by lupf on 2016/6/16.
 */
@Data
public class HeaderExt extends Header {

    private ExcelContentExt ext;

    public HeaderExt(String title, String field) {
        super(title, field);
    }

    public HeaderExt(String title, String field, ExcelContentExt ext) {
        super(title, field);
        this.ext = ext;
    }


}
