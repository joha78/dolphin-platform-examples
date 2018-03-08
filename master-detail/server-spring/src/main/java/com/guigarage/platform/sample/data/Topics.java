package com.guigarage.platform.sample.data;

import com.canoo.platform.remoting.server.event.Topic;

import java.util.concurrent.CopyOnWriteArrayList;

public interface Topics {

    Topic<String> CLEAR = Topic.create("clear");

    Topic<DataItem> REMOVE_LAST = Topic.create("removeLast");

    Topic<CopyOnWriteArrayList<DataItem>> NEW_ITEMS = Topic.create("add");

}
