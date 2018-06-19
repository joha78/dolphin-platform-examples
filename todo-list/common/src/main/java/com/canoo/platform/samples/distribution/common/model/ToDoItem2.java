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
package com.canoo.platform.samples.distribution.common.model;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class ToDoItem2 {

    private Property<String> text2;
    private Property<Boolean> completed2;

    public String getText2() {
        return text2.get();
    }
    public void setText2(String text) {
        this.text2.set(text);
    }
    public Property<String> getText2Property() {
        return text2;
    }


    public ToDoItem2 withText2(final String text) {
        setText2(text);
        return this;
    }


    public boolean isCompleted2() {
        return Boolean.TRUE == completed2.get();
    }
    public void setCompleted2(boolean completed) {
        this.completed2.set(completed);
    }
    public Property<Boolean> getCompleted2Property() {
        return completed2;
    }
    public ToDoItem2 withState2(final boolean state) {
        setCompleted2(state);
        return this;
    }

}
