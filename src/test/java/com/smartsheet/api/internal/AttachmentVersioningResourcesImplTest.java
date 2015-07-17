package com.smartsheet.api.internal;
/*
 * #[license]
 * Smartsheet SDK for Java
 * %%
 * Copyright (C) 2014 Smartsheet
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * %[license]
 */
import com.smartsheet.api.SmartsheetException;
import com.smartsheet.api.internal.http.DefaultHttpClient;
import com.smartsheet.api.models.Attachment;
import com.smartsheet.api.models.DataWrapper;
import com.smartsheet.api.models.PaginationParameters;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AttachmentVersioningResourcesImplTest extends ResourcesImplBase {

    private AttachmentVersioningResourcesImpl attachmentVersioningResources;

    @Before
    public void setUp() throws Exception {
        attachmentVersioningResources = new AttachmentVersioningResourcesImpl(new SmartsheetImpl("http://localhost:9090/1.1/", "accessToken",
                new DefaultHttpClient(), serializer));

    }

    @Test
    public void testDeleteAllVersions() throws SmartsheetException, IOException {
        server.setResponseBody(new File("src/test/resources/deleteAttachment.json"));

        attachmentVersioningResources.deleteAllVersions(1234L, 5678L);
    }

    @Test
    public void testListAllVersions() throws SmartsheetException, IOException {
        server.setResponseBody(new File("src/test/resources/listAttachmentVersions.json"));

        PaginationParameters parameters = new PaginationParameters(false, 1,1);
        DataWrapper<Attachment> attachments = attachmentVersioningResources.listAllVersions(1234L, 456L, parameters);
            assertNotNull(attachments.getData().get(0).getName());
            assertEquals(4583173393803140L, attachments.getData().get(0).getId().longValue());
            assertEquals("image/png", attachments.getData().get(1).getMimeType());
    }
}