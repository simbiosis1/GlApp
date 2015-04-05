package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.gl.admin.client.places.CoaList;
import org.simbiosis.ui.gl.admin.client.places.ReportConfig;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ CoaList.Tokenizer.class, ReportConfig.Tokenizer.class })
public interface AppHistoryMapper extends KembangHistoryMapper {

}
