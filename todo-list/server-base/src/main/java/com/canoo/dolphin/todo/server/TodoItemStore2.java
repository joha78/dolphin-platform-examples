/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dolphin.todo.server;

import com.canoo.platform.remoting.server.event.RemotingEventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.canoo.dolphin.todo.server.ToDoEventTopics.*;

public class TodoItemStore2 {

    private final RemotingEventBus eventBus;

    private final Map<String, Boolean> items = new HashMap<>();

    public TodoItemStore2(RemotingEventBus eventBus) {
        this.eventBus = eventBus;
    }

    void addItem(String name) {
        if(name != null && !name.isEmpty() && !items.containsKey(name)) {
            items.put(name, false);
            eventBus.publish(ITEM_ADDED2, name);
        }
    }

    void removeItem(String name) {
        items.remove(name);
        eventBus.publish(ITEM_REMOVED2, name);
    }

    void changeItemState(String name) {
        items.put(name, !items.get(name));
        eventBus.publish(ITEM_MARK_CHANGED2, name);
    }

    Stream<String> itemNameStream() {
        return items.keySet().stream();
    }

    boolean getState(String name) {
        return items.get(name);
    }
}
