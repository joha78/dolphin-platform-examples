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

import com.canoo.platform.core.functional.Subscription;
import com.canoo.platform.remoting.BeanManager;
import com.canoo.platform.remoting.server.Param;
import com.canoo.platform.remoting.server.RemotingAction;
import com.canoo.platform.remoting.server.RemotingController;
import com.canoo.platform.remoting.server.RemotingModel;
import com.canoo.platform.remoting.server.event.RemotingEventBus;
import com.canoo.platform.samples.distribution.common.model.ToDoItem2;
import com.canoo.platform.samples.distribution.common.model.ToDoList2;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.canoo.dolphin.todo.TodoAppConstants.*;
import static com.canoo.dolphin.todo.server.ToDoEventTopics.*;

@RemotingController(TODO_CONTROLLER2_NAME)
public class ToDoController2 {

    private final BeanManager beanManager;

    private final RemotingEventBus eventBus;

    private final TodoItemStore2 todoItemStore;

    private final List<Subscription> subscriptions = new ArrayList<>();

    @RemotingModel
    private ToDoList2 toDoList;

    @Inject
    public ToDoController2(BeanManager beanManager, RemotingEventBus eventBus, TodoItemStore2 todoItemStore) {
        this.beanManager = beanManager;
        this.eventBus = eventBus;
        this.todoItemStore = todoItemStore;
    }

    @PostConstruct
    public void onInit() {
        final Subscription changedSubscription = eventBus.subscribe(ITEM_MARK_CHANGED2, message -> updateItemState(message.getData()));
        final Subscription removedSubscription = eventBus.subscribe(ITEM_REMOVED2, message -> removeItem(message.getData()));
        final Subscription addedSubscritpion = eventBus.subscribe(ITEM_ADDED2, message -> addItem(message.getData()));

        subscriptions.add(changedSubscription);
        subscriptions.add(removedSubscription);
        subscriptions.add(addedSubscritpion);

        todoItemStore.itemNameStream().forEach(name -> addItem(name));
    }

    @PreDestroy
    public void onDestroy() {
        subscriptions.forEach(subscription -> subscription.unsubscribe());
    }

    @RemotingAction(ADD_ACTION)
    public void onItemAddAction() {
        todoItemStore.addItem(toDoList.getNewItemText2().get());
        toDoList.getNewItemText2().set("");
    }

    @RemotingAction(CHANGE_ACTION)
    public void onItemStateChangedAction(@Param(ITEM_PARAM) String name) {
        todoItemStore.changeItemState(name);
    }

    @RemotingAction(REMOVE_ACTION)
    public void onItemRemovedAction(@Param(ITEM_PARAM) String name) {
        todoItemStore.removeItem(name);
    }

    private void addItem(String name) {
        toDoList.getItems2().add(beanManager.create(ToDoItem2.class).withText2(name).withState2(todoItemStore.getState(name)));
    }

    private void removeItem(String name) {
        getItemByName(name).ifPresent(i -> toDoList.getItems2().remove(i));
    }

    private void updateItemState(String name) {
        getItemByName(name).ifPresent(i -> i.setCompleted2(todoItemStore.getState(name)));
    }

    private Optional<ToDoItem2> getItemByName(String name) {
        return toDoList.getItems2().stream().filter(i -> i.getText2().equals(name)).findAny();
    }
}
