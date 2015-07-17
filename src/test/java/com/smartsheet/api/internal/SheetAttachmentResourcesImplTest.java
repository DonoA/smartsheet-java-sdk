package com.smartsheet.api.internal;

import com.smartsheet.api.SmartsheetException;
import com.smartsheet.api.internal.http.DefaultHttpClient;
import com.smartsheet.api.models.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
public class SheetAttachmentResourcesImplTest extends ResourcesImplBase {

    private SheetAttachmentResourcesImpl sheetAttachmentResources;

    @Before
    public void setUp() throws Exception {
        sheetAttachmentResources = new SheetAttachmentResourcesImpl(new SmartsheetImpl("http://localhost:9090/1.1/",
                "accessToken", new DefaultHttpClient(), serializer));
    }

    @Test
    public void testDeleteAttachment() throws SmartsheetException, IOException {
        server.setResponseBody(new File("src/test/resources/deleteAttachment.json"));

        sheetAttachmentResources.deleteAttachment(1234L, 4567L);
    }

    @Test
    public void testAttachUrl() throws SmartsheetException, IOException {
        server.setResponseBody(new File("src/test/resources/attachLink.json"));

        Attachment attachment = new Attachment();
        attachment.setUrl("http://www.smartsheet.com/sites/all/themes/blue_sky/logo.png");
        attachment.setAttachmentType(AttachmentType.LINK);
        attachment.setUrlExpiresInMillis(1L);
        attachment.setAttachmentSubType(AttachmentSubType.PDF);

        Attachment newAttachment = sheetAttachmentResources.attachUrl(1234L, attachment);
        assertEquals("Search Engine", newAttachment.getName());
        assertEquals(AttachmentType.LINK, newAttachment.getAttachmentType());
    }

    @Test
    public void testGetAttachment() throws SmartsheetException, IOException {
        server.setResponseBody(new File("src/test/resources/getAttachment.json"));

        Attachment attachment = sheetAttachmentResources.getAttachment(1234L, 345L);
        assertNotNull(attachment.getUrl());
        assertEquals("AbstractResources.mup",attachment.getName());
    }

    @Test
    public void testListAttachments() throws SmartsheetException, IOException {
        server.setResponseBody(new File("src/test/resources/listAssociatedAttachments.json"));
        PaginationParameters parameters = new PaginationParameters(false, 1,1);

        DataWrapper<Attachment> attachments = sheetAttachmentResources.listAttachments(1234L, parameters);
        assertTrue(attachments.getTotalCount() == 2);
        assertTrue(attachments.getData().get(0).getId() == 4583173393803140L);
    }

    @Test
    public void testGetDiscussionAttachments() throws Exception {
        server.setResponseBody(new File("src/test/resources/listAssociatedAttachments.json"));
        PaginationParameters parameters = new PaginationParameters(false, 1,1);

        DataWrapper<Attachment> attachments = sheetAttachmentResources.getDiscussionAttachments(1234L, 456L, parameters);
        assertTrue(attachments.getTotalCount() == 2);
        assertTrue(attachments.getData().get(0).getId() == 4583173393803140L);
    }
}