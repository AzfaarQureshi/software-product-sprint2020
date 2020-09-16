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

package com.google.sps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.lang.Math;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    if(request.getDuration() > TimeRange.WHOLE_DAY.duration()) return new ArrayList<TimeRange>();

    ArrayList<TimeRange> eventTimeRanges = new ArrayList<TimeRange>();
    Collection<String> requestAttendees = request.getAttendees();
    for (Event e : events) {
      if(validEvent(e, requestAttendees)) eventTimeRanges.add(e.getWhen());
    }

    Collections.sort(eventTimeRanges, TimeRange.ORDER_BY_START);

    int pointer = TimeRange.START_OF_DAY;
    ArrayList<TimeRange> validTimeRanges = new ArrayList<TimeRange>();

    for (TimeRange t : eventTimeRanges) {
        int duration = t.start() - pointer;
        if (duration > 0 && duration >= request.getDuration())
          validTimeRanges.add(TimeRange.fromStartDuration(pointer, duration));
          pointer = Math.max(pointer, t.end());
    }
    if (TimeRange.END_OF_DAY - pointer > 0) {
      validTimeRanges.add(TimeRange.fromStartDuration(pointer, TimeRange.END_OF_DAY - pointer+1));
    }

    return validTimeRanges;
  }
  private static Boolean validEvent(Event curEvent, Collection<String> queryAttendees) {
    Set<String> curEventAttendees = curEvent.getAttendees();
    for(String attendee : queryAttendees) {
      if(curEventAttendees.contains(attendee)) return true;
    }
      return false;
  }
}

