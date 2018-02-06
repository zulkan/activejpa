/**
 * 
 */
package org.activejpa.enhancer;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.security.CodeSource;

import com.sun.tools.attach.VirtualMachine;

/**
 * @author ganeshs
 *
 */
public class ActiveJpaAgentLoaderImpl {
	
	/**
	 * Loads the java agent
	 */
	public static void loadAgent() {
		String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);
        
        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            CodeSource codeSource = ActiveJpaAgent.class.getProtectionDomain().getCodeSource();
            String path = new File(codeSource.getLocation().toURI()).getAbsolutePath();
            vm.loadAgent(path, "");
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}
