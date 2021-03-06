// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;
import com.google.gson.Gson;
import com.google.sps.data.Comments;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private Comments comments;

  @Override
  public void init() {
    comments = new Comments();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Comment");
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      String username = (String) entity.getProperty("userName");
      String comment = (String) entity.getProperty("comment");
      comments.addComment(username, comment);
    }

    String json = new Gson().toJson(comments.getComments());
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if(request.getParameter("user-name") == null || request.getParameter("comment") == null) {
      response.getWriter().println("username and comment string is required");
      return;
    }
    String comment = request.getParameter("comment");
    if (Boolean.parseBoolean(getParameter(request, "upper-case", "false"))) {
      comment = comment.toUpperCase();
    }
    // c.addComment(request.getParameter("user-name"), comment);
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("userName", request.getParameter("user-name"));
    commentEntity.setProperty("comment", comment);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);


    response.sendRedirect("/index.html");
  }

  private String convertToJsonUsingGson(ArrayList message) {
    Gson gson = new Gson();
    String json = gson.toJson(message);
    return json;
  }
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}

