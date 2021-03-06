package org.eclipse.jst.jsf.test.util.mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jst.jsf.test.util.ZipStreamWrapper;
import org.osgi.framework.Bundle;

/**
 * A test context that can be used to construct a number of MockResources that
 * are associated with a mocked workspace/project hierarchy
 * 
 * @author cbateman
 * 
 */
public class MockWorkspaceContext implements IWorkspaceContextWithEvents
{
    private final MockWorkspace _ws;
    private final Map<IPath, MockResource> _ownedResources;

    public MockWorkspaceContext(final MockWorkspace ws)
    {
        _ws = ws;
        _ownedResources = new HashMap<IPath, MockResource>();
        ((MockWorkspaceRoot) ws.getRoot()).setContext(this);
    }

    public MockWorkspaceContext()
    {
        this(new MockWorkspace(new MockWorkspaceRoot()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#fireWorkspaceEvent
     * (org.eclipse.core.resources.IResourceChangeEvent)
     */
    public void fireWorkspaceEvent(final IResourceChangeEvent event)
    {
        _ws.fireResourceChangeEvent(event);
    }

    public void init() throws Exception
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#dispose()
     */
    public void dispose() throws Exception
    {
        for (final Map.Entry<IPath, MockResource> entry : _ownedResources
                .entrySet())
        {
            entry.getValue().dispose();
        }
        _ownedResources.clear();
        _ws.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#getWorkspace()
     */
    public MockWorkspace getWorkspace()
    {
        return _ws;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#getResource(org.
     * eclipse.core.runtime.IPath)
     */
    public IResource getResource(final IPath path)
    {
        return _ownedResources.get(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#getFile(org.eclipse
     * .core.runtime.IPath)
     */
    public IFile getFile(final IPath path)
    {
        return (MockFile) getResource(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#getProject(org.eclipse
     * .core.runtime.IPath)
     */
    public IProject getProject(final IPath path)
    {
        return (MockProject) _ownedResources.get(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#createProject(org
     * .eclipse.core.runtime.IPath, boolean)
     */
    private IProject createProject(final IPath path, final boolean replace)
    {
        final MockProject project = new MockProject(path,
                new MyMockResourceFactoryFromZipFile());
        attachProject(project, replace);
        return project;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#createProject(java
     * .lang.String)
     */
    public IProject createProject(final String baseId)
    {
        int i = 0;
        while (_ownedResources.get(generateName(baseId, i)) != null)
        {
            // keep looping until we get baseId_i that doesn't exist
            i++;
        }
        return createProject(generateName(baseId, i), false);
    }

    private Path generateName(final String baseId, final int i)
    {
        return new Path(baseId + "_TestProject_" + i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#createProject(org
     * .eclipse.core.runtime.IPath)
     */
    public IProject createProject(final IPath path)
    {
        return createProject(path, false);
    }

    private void attachProject(final IProject project, final boolean replace)
    {
        checkExists(project.getFullPath(), replace);
        ((MockProject) project).setWorkspace(_ws);
        _ownedResources.put(project.getFullPath(), (MockProject) project);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#loadProject(org.
     * eclipse.core.runtime.IPath, org.eclipse.jst.jsf.test.util.mock.
     * IWorkspaceContext.ZipFileLoader)
     */
    public IProject loadProject(final IPath path,
            final ZipFileLoader zipFileLoader) throws Exception
    {
        checkExists(path, false);
        final MockProject project = new MockProject(path,
                new MyMockResourceFactoryFromZipFile(
                        zipFileLoader.getZipFile(),
                        zipFileLoader.getPathInZip()));
        attachProject(project, false);
        return project;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#loadProject(
     * org.eclipse.core.runtime.IPath, org.osgi.framework.Bundle,
     * java.lang.String)
     */
    public IProject loadProject(IPath path, Bundle bundle, String pathIntoZip) throws Exception {
        checkExists(path, false);
        final MockProject project = new MockProject(path,
                new MyMockResourceFactoryFromBundle(bundle, pathIntoZip));
        attachProject(project, false);
        return project;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.test.util.mock.IWorkspaceContext#attachFile(org.eclipse
     * .jst.jsf.test.util.mock.MockProject, org.eclipse.core.runtime.IPath,
     * java.io.File)
     */
    public MockFile attachFile(final IProject project, final IPath projectRelativePath,
            final File file)
    {
        assertEquals(checkExists(project.getFullPath(), true), project);
        // throw an exception if projectRelativePath already exists
        checkExists(project.getFullPath().append(projectRelativePath), false);
        assertTrue(file.exists());
        final MockFile iFile = (MockFile) project.getFile(projectRelativePath);
        InputStream inStream = null;
        try
        {
            inStream = new FileInputStream(file);
            iFile.setContents(inStream, 0, null);
            return iFile;
        } catch (final IOException e)
        {
            throw new RuntimeException(e);
        } catch (final CoreException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            if (inStream != null)
            {
                try
                {
                    inStream.close();
                } catch (final IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private MockResource checkExists(final IPath path, final boolean replace)
    {
        final MockResource resource = _ownedResources.get(path);
        if (resource != null && !replace)
        {
            throw new IllegalArgumentException(path.toString()
                    + " already exists");
        }
        return resource;
    }

    private abstract class AbstractMockResourceFactory implements IMockResourceFactory
    {

        public MockFolder createFolder(final MockContainer container,
                final IPath path)
        {
            final IPath newFileFullPath = container.getFullPath().append(path);
            MockResource resource = checkExists(newFileFullPath, true);
            if (resource == null)
            {
                resource = newFolder(container, newFileFullPath);
                ensurePathToNewResource(container, path);
            }
            return (MockFolder) resource;
        }

        protected void ensurePathToNewResource(final MockContainer container,
                final IPath path)
        {
            // add any intervening MockContainers for the folders
            // under container where the file lives
            IPath leadingPath = path.removeLastSegments(1);
            IPath curPath = container.getFullPath();
            while (leadingPath.segmentCount() > 0)
            {
                final String nextSegment = leadingPath.segments()[0];
                curPath = curPath.append(nextSegment);
                leadingPath = leadingPath.removeFirstSegments(1);
                final MockResource newContainer = checkExists(curPath, true);
                if (newContainer == null)
                {
                    newFolder(container, curPath);
                }
            }
        }

        protected MockResource newFolder(final MockContainer container, final IPath curPath)
        {
            MockResource newContainer;
            newContainer = new MockFolder(curPath, this);
            newContainer.setWorkspace(_ws);
            newContainer.setProject(container.getProject());
            _ownedResources.put(curPath, newContainer);
            return newContainer;
        }

        public List<MockResource> getCurrentMembers(
                final MockContainer container)
        {
            final List<MockResource> members = new ArrayList<MockResource>();
            final IPath containerPath = container.getFullPath();
            for (final IPath path : _ownedResources.keySet())
            {
                // path is a member of container if container's path is
                // a prefix of path and path has only one extra segment
                if (containerPath.isPrefixOf(path)
                        && path.segmentCount() == containerPath.segmentCount() + 1)
                {
                    members.add(_ownedResources.get(path));
                }
            }
            return members;
        }

        public MockFile createFile(final MockContainer container,
                final IPath path) throws Exception
        {
            return createFile(container, path, null);
        }

        protected MockFile createFile(final MockContainer container,
                final IPath path, final InputStream contents) throws Exception
        {
            final IPath newFileFullPath = container.getFullPath().append(path);
            MockResource resource = checkExists(newFileFullPath, true);
            if (resource == null)
            {
                resource = new MockFile(newFileFullPath);
                resource.setWorkspace(getWorkspace());
                resource.setProject(container.getProject());
                if (contents != null)
                {
                    ((MockFile) resource).setContents(contents, false, true, new NullProgressMonitor());
                } else
                {
                    setContents((MockFile) resource, path);
                }
                ensurePathToNewResource(container, path);
                _ownedResources.put(newFileFullPath, resource);
            }

            return (MockFile) resource;
        }

        protected abstract void setContents(final MockFile file, final IPath path) throws Exception;
    }

    private class MyMockResourceFactoryFromZipFile extends AbstractMockResourceFactory
    {
        private final ZipFile _zip;
        private final String _pathIntoZip;

        public MyMockResourceFactoryFromZipFile()
        {
            _zip = null;
            _pathIntoZip = "";
        }

        public MyMockResourceFactoryFromZipFile(final ZipFile zip, final String pathIntoZip)
        {
            _zip = zip;
            _pathIntoZip = pathIntoZip;
        }

        public void dispose() throws Exception
        {
            if (_zip != null)
            {
                _zip.close();
            }
        }

        @Override
        protected void setContents(MockFile file, IPath path) throws Exception {
            if (_zip != null)
            {
                final ZipEntry entry = _zip.getEntry(_pathIntoZip
                        + path.toString());
                if (entry != null)
                {
                    final InputStream inputStream = _zip
                            .getInputStream(entry);
                    if (inputStream != null)
                    {
                        file.setContents(inputStream, false,
                                true, new NullProgressMonitor());
                    }
                }
            }
        }

        public void forceLoad(final MockProject project) throws Exception
        {
            final Enumeration<? extends ZipEntry> entries = _zip.entries();
            while (entries.hasMoreElements())
            {
                final ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                final int removeIdx = name.indexOf(_pathIntoZip);
                if (removeIdx > -1)
                {
                    name = name.substring(_pathIntoZip.length());
                    if (entry.isDirectory())
                    {
                        if (name.endsWith("/"))
                        {
                            name = name.substring(0, name.length() - 1);
                        }

                        if (name.length() > 0)
                        {
                            createFolder(project, new Path(name));
                        }
                    } else
                    {
                        createFile(project, new Path(name));
                    }
                }
            }
        }
    }

    private class MyMockResourceFactoryFromBundle extends AbstractMockResourceFactory
    {
        private final URL _zipURL;
        private final String _pathIntoZip;

        public MyMockResourceFactoryFromBundle(Bundle bundle, String pathIntoZip)
        {
            _zipURL = FileLocator.find(bundle, new Path(pathIntoZip), null);
            Assert.assertNotNull(_zipURL);
            _pathIntoZip = getPrefix();
        }

        public void dispose() throws Exception
        {
        }

        private String getPrefix()
        {
            ZipInputStream zis = null;
            try
            {
                zis = new ZipInputStream(_zipURL.openStream());
                ZipEntry entry = zis.getNextEntry();
                while (entry != null)
                {
                    String name = entry.getName();
                    if (name != null && name.endsWith(".project")
                            && !entry.isDirectory())
                    {
                        int index = name.lastIndexOf(".project");
                        return name.substring(0, index);
                    }
                    entry = zis.getNextEntry();
                }
            } catch (IOException e)
            {
                Assert.fail("Problem with zip file: " + _zipURL.toString());
            } finally
            {
                try
                {
                    if (zis != null)
                        zis.close();
                } catch (Exception ex)
                {
                    // ignore
                }
            }

            // if we get to here, then nothing to prepend
            return "";
        }

        @Override
        protected void setContents(final MockFile file, final IPath path) throws Exception
        {
            ZipInputStream zis = null;
            try
            {
                zis = new ZipInputStream(_zipURL.openStream());
                ZipEntry entry = zis.getNextEntry();
                while (entry != null)
                {
                    String name = entry.getName();
                    final int index = name.indexOf(_pathIntoZip
                            + path.toString());
                    if (index > -1)
                    {
                        name = name.substring(_pathIntoZip.length());
                        if (!entry.isDirectory())
                        {
                            file.setContents(new ZipStreamWrapper(zis),
                                    false, true, new NullProgressMonitor());
                            return;
                        }
                    }
                    entry = zis.getNextEntry();
                }
            } finally
            {
                try
                {
                    if (zis != null)
                        zis.close();
                } catch (Exception ex)
                {
                    // ignore
                }
            }
        }

        public void forceLoad(final MockProject project) throws Exception
        {
            ZipInputStream zis = null;
            try
            {
                zis = new ZipInputStream(_zipURL.openStream());
                ZipEntry entry = zis.getNextEntry();
                while (entry != null)
                {
                    String name = entry.getName();
                    final int removeIdx = name.indexOf(_pathIntoZip);
                    if (removeIdx > -1)
                    {
                        name = name.substring(_pathIntoZip.length());
                        if (entry.isDirectory())
                        {
                            if (name.endsWith("/"))
                            {
                                name = name.substring(0, name.length() - 1);
                            }

                            if (name.length() > 0)
                            {
                                createFolder(project, new Path(name));
                            }
                        } else
                        {
                            createFile(project, new Path(name), new ZipStreamWrapper(zis));
                        }
                    }
                    entry = zis.getNextEntry();
                }
            } finally
            {
                try
                {
                    if (zis != null)
                        zis.close();
                } catch (Exception ex)
                {
                    // ignore
                }
            }
        }
    }

    public List<IResourceChangeListener> getListeners()
    {
        return getWorkspace().getListeners();
    }

    public List<IResourceChangeListener> getListeners(final List<Class<? extends IResourceChangeListener>>  includeListeners)
    {
        final List<IResourceChangeListener> listeners = new ArrayList<IResourceChangeListener>();
        for (final IResourceChangeListener listener : getListeners())
        {
            SEARCH_CLASSES: for (final Class<? extends IResourceChangeListener> clazz : includeListeners)
            {
                if (clazz.isAssignableFrom(listener.getClass()))
                {
                    listeners.add(listener);
                    break SEARCH_CLASSES;
                }
            }
        }
        return listeners;
    }

    public void ensureAllMembers(final IProject project) throws Exception
    {
        ((MockProject) project).loadAllMembers();
    }
}
