package com.panjohnny.baka4j.v3;

import com.panjohnny.baka4j.BakaClient;
import com.panjohnny.baka4j.v3.impl.V3WrapperClientImpl;
import com.panjohnny.baka4j.v3.records.ApiInfo;
import com.panjohnny.baka4j.v3.records.GDPRCommissioner;
import com.panjohnny.baka4j.v3.records.StudentAbsence;
import com.panjohnny.baka4j.util.RestAction;

public sealed interface V3WrapperClient extends BakaClient permits V3WrapperClientImpl {
    static V3WrapperClient getInstance(String url) {
        return new V3WrapperClientImpl(url);
    }
    /**
     * <h1>/api</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/API_info.md">...</a>
     * @see ApiInfo
     */
    RestAction<ApiInfo[]> apis();

    /**
     * <h1>/api/3</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/API_info.md">...</a>
     * @see ApiInfo
     */
    RestAction<ApiInfo> apiInfo();

    /**
     * <h1>/api/3/absence/student</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/absence.md">...</a>
     */
    RestAction<StudentAbsence> absence();

    // TODO events

    /**
     * <h1>/api/3/gdpr/commissioners</h1>
     * Docs: <a href="https://github.com/bakalari-api/bakalari-api-v3/blob/master/moduly/gdpr.md">...</a>
     * @apiNote May be forbidden for "student" profiles
     */
    RestAction<GDPRCommissioner[]> gdprCommissioners();
}
