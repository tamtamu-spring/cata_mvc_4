package org.sid.web;

import javax.validation.Valid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProduitController {
	@Autowired  // == je demande à Spring IoC d'injecter un objet de type ProduitRepository.
	private ProduitRepository produitRepository;

	// chaque méthode retourne un String qui représente le nom de la vue à afficher.
	@RequestMapping(value="/index")
	public String index(Model model, @RequestParam(name="page", defaultValue="0") int p, 
			                         @RequestParam(name="size", defaultValue="5") int s,
			                         @RequestParam(name="motCle", defaultValue="") String mc){
		//Page<Produit> pageProduits = produitRepository.findAll(new PageRequest(p,s)); // findAll() de Spring Data
		Page<Produit> pageProduits = produitRepository.chercher("%"+mc+"%", new PageRequest(p,s));
		model.addAttribute("listproduits", pageProduits.getContent());
		int[] pages = new int[pageProduits.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("size", s);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "produits"; // DispatcherServlet il va chercher dans template la vue appelée
		                   // "produits.html" pour qu'elle soit envoyée au client.
	}
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(Long id, String motCle, int page, int size){
		produitRepository.delete(id);
		return "redirect:/index?page="+page+"&size="+size+"&motCle="+motCle;
	}
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String formProduit(Model model){
		model.addAttribute("produit", new Produit());
		return "formProduit";
	}
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(Model model, @Valid Produit produit, BindingResult bindingResult){
		                                             //Spring fait le data binding implicitement des paramètres 
		                                             //du formulaire avec les données de la requête à condition de donner les mêmes noms aux elements 
		                                             //input(html) que les noms des attributs de l'entité Produit.
		// via l'annotation @valid, on demande à Spring de faire la validation avant de stocker dans l'entité.
		if(bindingResult.hasErrors())
			return "formProduit";
		produitRepository.save(produit);// si id est null il fait "insert" et si id !null et existe déja il fait update
		return "confirmation";
	}
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String editer(Model model, Long id){
		Produit p = produitRepository.findOne(id);
		model.addAttribute("produit", p);
		return "editProduit";
	}
	@RequestMapping(value="/")
	public String home(){
		return "redirect:/index";
	}
	@RequestMapping(value="/403")
	public String accessDenied(){
		return "403";
	}
	@RequestMapping(value="/login")
	public String authentifier(){
		return "login";
	}
}
