package com.hrstd.manager;

import java.util.List;

public class ServerManager {
    private List<PlayerManager> pms;

    public ServerManager(PlayerManager pm) {
        this(new PlayerManager[] { pm });
    }

    public ServerManager(PlayerManager[] pms) {
        this.pms = List.of(pms);
    }


}
