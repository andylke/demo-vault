package com.github.andylke.demo.vault;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;

@Configuration
class VaultConfiguration {

  @PostConstruct
  void configure() throws VaultException {
    String vaultToken = System.getProperty("vault.token");
    String vaultAddr = System.getProperty("vault.addr");

    System.out.format("Using Vault Host %s\n", vaultAddr);
    System.out.format("With Vault Token %s\n", vaultToken);
    /* This should be a separate method called from Main, then
     * again for simplicity...
     */
    final VaultConfig config = new VaultConfig().address(vaultAddr).token(vaultToken).build();
    final Vault vault = new Vault(config);

    vault.auth();

    try {
      final Map<String, String> vaultEntries = vault.logical().read("secret/hello").getData();
      System.out.format("entries in secret/hello is " + vaultEntries + "\n");
    } catch (VaultException e) {
      System.out.println("Exception thrown: " + e);
    }
  }
}
