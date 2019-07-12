package com.lei.smart;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

public class LeiPreconditions {
     /**
     * 检查参数是否按照要求填写  如有有一个不符合 返回false
     * @param params
     * @return
     */
    public static void checkNotNull(String ...params) {
        for (String param : params) {
            if (StringUtils.isBlank(param)) {
                throw new UserException(ResponseCodeEnum.PARAM_NEED);
            }
        }
    }
}
