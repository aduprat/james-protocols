/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.apache.james.protocols.api;

/**
 * Handler which can be used to gather statistics for {@link ConnectHandler} or 
 * modify the return type of them
 * 
 *
 * @param <S>
 */
public interface ConnectHandlerResultHandler<S extends ProtocolSession> extends ProtocolHandler{
    
    /**
     * Called after the {@link ConnectHandler} returned a result
     * 
     * @param session
     * @param response
     * @param executionTime
     * @param handler
     * @return result
     */
    boolean onResponse(ProtocolSession session, boolean response, long executionTime, ConnectHandler<S> handler);

}
