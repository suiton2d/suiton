package com.nebula2d.editor.util;

/**
 * Created with IntelliJ IDEA.
 * User: bonazza
 * Date: 8/2/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlatformUtil {

    public static int findSlashChar(String base) {
        int slash = base.indexOf('/');

        if (slash == -1) {
            slash = base.indexOf('\\');
        }

        return slash;
    }
}
