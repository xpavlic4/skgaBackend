package org.jboss.as.quickstarts.kitchensink.test;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MemberRegistrationTest {
/*   @Deployment
   public static Archive<?> createTestArchive() {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClasses(Member.class, SyncingBean.class, Resources.class)
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            // Deploy our test datasource
            .addAsWebInfResource("test-ds.xml", "test-ds.xml");
   }

   @Inject
   SyncingBean memberRegistration;

   @Inject
   Logger log;

   @Test
   public void testRegister() throws Exception {
      Member newMember = new Member();
      newMember.setName("Jane Doe");
      newMember.setEmail("jane@mailinator.com");
      newMember.setPhoneNumber("2125551234");
      memberRegistration.register();
      assertNotNull(newMember.getId());
      log.info(newMember.getName() + " was persisted with id " + newMember.getId());
   }
     */
}
