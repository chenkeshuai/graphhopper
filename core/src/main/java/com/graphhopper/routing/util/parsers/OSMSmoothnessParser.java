/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.EncodedValueLookup;
import com.graphhopper.routing.ev.EnumEncodedValue;
import com.graphhopper.routing.ev.Smoothness;
import com.graphhopper.storage.IntsRef;

import java.util.List;

import static com.graphhopper.routing.ev.Smoothness.KEY;
import static com.graphhopper.routing.ev.Smoothness.MISSING;

public class OSMSmoothnessParser implements TagParser {

    private final EnumEncodedValue<Smoothness> smoothnessEnc;

    public OSMSmoothnessParser() {
        this(new EnumEncodedValue<>(KEY, Smoothness.class));
    }

    public OSMSmoothnessParser(EnumEncodedValue<Smoothness> smoothnessEnc) {
        this.smoothnessEnc = smoothnessEnc;
    }

    @Override
    public void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> list) {
        list.add(smoothnessEnc);
    }

    @Override
    public IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay readerWay, IntsRef relationFlags) {
        String smoothnessTag = readerWay.getTag("smoothness");
        Smoothness smoothness = Smoothness.find(smoothnessTag);
        if (smoothness == MISSING)
            return edgeFlags;

        smoothnessEnc.setEnum(false, edgeFlags, smoothness);
        return edgeFlags;
    }
}