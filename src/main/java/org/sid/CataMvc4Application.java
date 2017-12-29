package org.sid;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CataMvc4Application {

	public static void main(String[] args) {
		/*ApplicationContext ctx =*/ SpringApplication.run(CataMvc4Application.class, args);
		//ProduitRepository produitRepository = ctx.getBean(ProduitRepository.class);
		/*produitRepository.save(new Produit("Braun shaver G50 4", 900, 2));
		produitRepository.save(new Produit("Nokia 8 4", 1500, 1));
		produitRepository.save(new Produit("Asus R500A 4", 630, 3));
		produitRepository.save(new Produit("Alpha-romeo Julietta 4", 160000, 1));
		produitRepository.save(new Produit("Braun shaver G50 5", 900, 2));
		produitRepository.save(new Produit("Nokia 8 5", 1500, 1));
		produitRepository.save(new Produit("Asus R500A 5", 630, 3));
		produitRepository.save(new Produit("Alpha-romeo Julietta 5", 160000, 1));*/
		
		//produitRepository.findAll().forEach(p->System.out.println(p.getDesignation()));
	}
}
