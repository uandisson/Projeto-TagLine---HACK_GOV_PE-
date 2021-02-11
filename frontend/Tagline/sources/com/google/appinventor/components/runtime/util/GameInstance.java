package com.google.appinventor.components.runtime.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInstance {
    private List<String> IhlDYVsQmgat6F3NXqRok975lHQlAvyJICX3QHDdE383xYIGTapMORiCm1KjyWCi;
    private Map<String, String> KbzcIEn6WDqjdY1QBot1TMrBwhEYy4xAUKG2cbzQ22VNohlOtuBGKUJsEeMNZyEH;
    private String fYawfM9zby4GKauiBqb4ci1jAzhTnTs5rPk6pEfhgdCGlaj30ALyaese8PExemNE = "";
    private String h8Png9oaVDfYGvgWdHcn1DMBnn2tbT5MRZoUvXvvhLrY5O6ybby2QmTOJ6PZJucW;

    public GameInstance(String str) {
        List<String> list;
        Map<String, String> map;
        new ArrayList(0);
        this.IhlDYVsQmgat6F3NXqRok975lHQlAvyJICX3QHDdE383xYIGTapMORiCm1KjyWCi = list;
        new HashMap();
        this.KbzcIEn6WDqjdY1QBot1TMrBwhEYy4xAUKG2cbzQ22VNohlOtuBGKUJsEeMNZyEH = map;
        this.h8Png9oaVDfYGvgWdHcn1DMBnn2tbT5MRZoUvXvvhLrY5O6ybby2QmTOJ6PZJucW = str;
    }

    public String getInstanceId() {
        return this.h8Png9oaVDfYGvgWdHcn1DMBnn2tbT5MRZoUvXvvhLrY5O6ybby2QmTOJ6PZJucW;
    }

    public String getLeader() {
        return this.fYawfM9zby4GKauiBqb4ci1jAzhTnTs5rPk6pEfhgdCGlaj30ALyaese8PExemNE;
    }

    public void setLeader(String str) {
        String str2 = str;
        this.fYawfM9zby4GKauiBqb4ci1jAzhTnTs5rPk6pEfhgdCGlaj30ALyaese8PExemNE = str2;
    }

    public PlayerListDelta setPlayers(List<String> list) {
        List list2;
        List<String> list3;
        PlayerListDelta playerListDelta;
        List<String> list4 = list;
        if (list4.equals(this.IhlDYVsQmgat6F3NXqRok975lHQlAvyJICX3QHDdE383xYIGTapMORiCm1KjyWCi)) {
            return PlayerListDelta.NO_CHANGE;
        }
        List<String> list5 = this.IhlDYVsQmgat6F3NXqRok975lHQlAvyJICX3QHDdE383xYIGTapMORiCm1KjyWCi;
        new ArrayList(list4);
        List list6 = list2;
        new ArrayList(list4);
        this.IhlDYVsQmgat6F3NXqRok975lHQlAvyJICX3QHDdE383xYIGTapMORiCm1KjyWCi = list3;
        boolean removeAll = list6.removeAll(list5);
        boolean removeAll2 = list5.removeAll(list4);
        if (list6.size() == 0 && list5.size() == 0) {
            return PlayerListDelta.NO_CHANGE;
        }
        new PlayerListDelta(list5, list6);
        return playerListDelta;
    }

    public List<String> getPlayers() {
        return this.IhlDYVsQmgat6F3NXqRok975lHQlAvyJICX3QHDdE383xYIGTapMORiCm1KjyWCi;
    }

    public String getMessageTime(String str) {
        String str2 = str;
        if (this.KbzcIEn6WDqjdY1QBot1TMrBwhEYy4xAUKG2cbzQ22VNohlOtuBGKUJsEeMNZyEH.containsKey(str2)) {
            return this.KbzcIEn6WDqjdY1QBot1TMrBwhEYy4xAUKG2cbzQ22VNohlOtuBGKUJsEeMNZyEH.get(str2);
        }
        return "";
    }

    public void putMessageTime(String str, String str2) {
        String put = this.KbzcIEn6WDqjdY1QBot1TMrBwhEYy4xAUKG2cbzQ22VNohlOtuBGKUJsEeMNZyEH.put(str, str2);
    }
}
