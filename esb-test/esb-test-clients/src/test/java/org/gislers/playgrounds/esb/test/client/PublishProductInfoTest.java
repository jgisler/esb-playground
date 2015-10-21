package org.gislers.playgrounds.esb.test.client;

import org.gislers.playgrounds.esb.test.client.service.PublishProductService;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.jaxrs.SupportJaxRs;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Created by:   jgisle
 * Created date: 10/21/15
 */
@SupportJaxRs
@RunWith(CdiRunner.class)
public class PublishProductInfoTest {

    @Inject
    private PublishProductService publishProductService;

//    @Test
//    public void testPublish() {
//
//        Map<String, String> stats = publishProductService.batchSend( 1 );
//        assertNotNull( stats );
//        assertEquals( 1, stats.size() );
//    }
}
