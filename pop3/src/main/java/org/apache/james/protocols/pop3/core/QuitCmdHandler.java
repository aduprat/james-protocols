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

package org.apache.james.protocols.pop3.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.james.protocols.api.Request;
import org.apache.james.protocols.api.Response;
import org.apache.james.protocols.api.handler.CommandHandler;
import org.apache.james.protocols.pop3.POP3Response;
import org.apache.james.protocols.pop3.POP3Session;
import org.apache.james.protocols.pop3.mailbox.Mailbox;

/**
 * Handles QUIT command
 */
public class QuitCmdHandler implements CommandHandler<POP3Session> {
    private final static String COMMAND_NAME = "QUIT";

    /**
     * Handler method called upon receipt of a QUIT command. This method handles
     * cleanup of the POP3Handler state.
     */
    @SuppressWarnings("unchecked")
    public Response onCommand(POP3Session session, Request request) {
        POP3Response response = null;
        if (session.getHandlerState() == POP3Session.AUTHENTICATION_READY || session.getHandlerState() == POP3Session.AUTHENTICATION_USERSET) {
            response = new POP3Response(POP3Response.OK_RESPONSE, "Apache James POP3 Server signing off.");
            response.setEndSession(true);
            return response;
        }
        List<Long> toBeRemoved = (List<Long>) session.getState().get(POP3Session.DELETED_UID_LIST);
        try {
            Mailbox mailbox = session.getUserMailbox();
            long uids[] = new long[toBeRemoved.size()];
            for (int i = 0;i < toBeRemoved.size(); i++) {
            	uids[i] = toBeRemoved.get(i);
            }
            mailbox.remove(uids);
            response = new POP3Response(POP3Response.OK_RESPONSE, "Apache James POP3 Server signing off.");
        } catch (Exception ex) {
            response = new POP3Response(POP3Response.ERR_RESPONSE, "Some deleted messages were not removed");
            session.getLogger().error("Some deleted messages were not removed", ex);
        }
        response.setEndSession(true);
      
        return response;
    }

    /**
     * @see org.apache.james.protocols.api.handler.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
        List<String> commands = new ArrayList<String>();
        commands.add(COMMAND_NAME);
        return commands;
    }

}