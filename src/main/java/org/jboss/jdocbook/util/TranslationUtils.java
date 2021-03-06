/*
 * jDocBook, processing of DocBook sources
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.jboss.jdocbook.util;

import java.io.File;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Collection of utilities for dealing with i18n support, as defined by GNU gettext.
 *
 * @author Steve Ebersole
 */
public class TranslationUtils {
	/**
	 * Is the given file a GNU gettext POT file?
	 * <p/>
	 * The determination here is made solely upon the file extension currently.
	 *
	 * @param file The file to check.
	 * @return True if it is considered a POT file; false otherwise.
	 */
	public static boolean isPotFile(File file) {
		return "pot".equals( FileUtils.getExtension( file.getName() ) );
	}

	/**
	 * Given a source file, determine its correspnding GNU gettext POT file name.
	 *
	 * @param source The source file.
	 * @return The corresponding POT file name.
	 */
	public static String determinePotFileName(File source) {
		return FileUtils.removeExtension( source.getName() ) + ".pot";
	}

	/**
	 * Given a source file (or a POT file), determine its correspnding GNU gettext PO file name.
	 *
	 * @param template The source (or POT) file.
	 * @return The corresponding PO file name.
	 */
	public static String determinePoFileName(File template) {
		return FileUtils.removeExtension( template.getName() ) + ".po";
	}

	public static Locale parse(String locale) {
		return parse( locale, '-' );
	}

	public static Locale parse(String locale, char sep) {
		StringTokenizer tokens = new StringTokenizer( locale, "" + sep );
		int tokencount = tokens.countTokens();
		switch ( tokencount ) {
			case 3 :
				return new Locale( tokens.nextToken(), tokens.nextToken(), tokens.nextToken() );
			case 2 :
				return new Locale( tokens.nextToken(), tokens.nextToken() );
			case 1 :
				return new Locale( tokens.nextToken() );
			default:
				return new Locale( "tbd" );
		}
	}

	public static String render(Locale locale, char sep) {
        boolean l = locale.getLanguage().length() != 0;
        boolean c = locale.getCountry().length() != 0;
        boolean v = locale.getVariant().length() != 0;
        StringBuffer result = new StringBuffer( locale.getLanguage() );
        if (c||(l&&v)) {
            result.append( sep ).append( locale.getCountry() );
        }
        if (v&&(l||c)) {
            result.append( sep ).append( locale.getVariant() );
        }
        return result.toString();
	}
}
