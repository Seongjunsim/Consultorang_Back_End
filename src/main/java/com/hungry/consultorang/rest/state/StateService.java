package com.hungry.consultorang.rest.state;

import com.hungry.consultorang.model.state.GetComparisionResponseModel;
import com.hungry.consultorang.model.state.GetComparisonRequestModel;

public interface StateService {

    void setAnual() throws Exception;
    GetComparisionResponseModel getComparison(GetComparisonRequestModel param)
        throws Exception;
}
