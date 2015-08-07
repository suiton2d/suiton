/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) $date.year Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.suiton2d.util;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class ByteUtilsTest {

    @Test
    public void testDecodeBase64String() {
        byte[] bytes = "foo".getBytes(Charsets.US_ASCII);
        String encodedString = BaseEncoding.base64().encode(bytes);
        String decodedString = ByteUtils.decodeBase64String(encodedString);

        assertEquals("foo", decodedString);
    }
}
