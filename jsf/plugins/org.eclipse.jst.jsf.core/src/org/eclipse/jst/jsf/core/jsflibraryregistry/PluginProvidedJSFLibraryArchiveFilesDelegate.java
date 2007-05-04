package org.eclipse.jst.jsf.core.jsflibraryregistry;

import org.eclipse.jst.jsf.core.jsflibraryregistry.internal.PluginProvidedJSFLibraryCreationHelper2;

/**
 * Abstract class to be used for supplying the jar file names for a plugin provided jsf library.
 * <br>Adopters must implement getArchiveFiles() making use of the addArchiveFile(String pluginRootRelativePath) method.
 * <br><em>Note: A plugin providing jsf libraries cannot be jarred at this time. </em>
 * <br><br>ex.
 * <br>  <pre><code>public void getArchiveFiles() {
						addArchiveFile("/lib/JAR1.jar");
						addArchiveFile("/lib/JAR2.jar");
					}
		</code>
		</pre>
 *
 */
public abstract class PluginProvidedJSFLibraryArchiveFilesDelegate {
	private PluginProvidedJSFLibraryCreationHelper2 helper;
	
	/**
	 * Constructs an instance.
	 */
	public PluginProvidedJSFLibraryArchiveFilesDelegate() {
		super();	
	}
	
	/**
	 * Concrete delegate must implement this method to define jars in the library.
	 * Use addJarFile(String pluginRootRelativePath) within this method to add jars to the library.
	 */
	public abstract void getArchiveFiles(); 
	
	/**
	 * Adds jar file to the library.  Verification of file existence does not occur at this point.
	 * 
	 * Jar must be specified as being relative to the plugin.  
	 * ex.  "/lib/MyJar.jar" where the lib directory is a child of the root.
	 *  
	 * @param pluginRootRelativePath
	 */
	protected void addArchiveFile(String pluginRootRelativePath) {
		helper.addArchiveFile(pluginRootRelativePath);//getArchiveFiles().add(helper.createArchiveFile(pluginRootRelativePath));
	}

	/**
	 * Not to be implemented by subclasses
	 * @param helper
	 */
	public void setCreationHelper(
			PluginProvidedJSFLibraryCreationHelper2 helper) {		
		this.helper = helper;
	}
}
