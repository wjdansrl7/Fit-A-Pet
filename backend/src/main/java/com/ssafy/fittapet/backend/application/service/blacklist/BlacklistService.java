package com.ssafy.fittapet.backend.application.service.blacklist;

import com.ssafy.fittapet.backend.domain.entity.Blacklist;

public interface BlacklistService {

    boolean existsById(String refresh);

    void saveBlacklist(Blacklist blacklist);
}
