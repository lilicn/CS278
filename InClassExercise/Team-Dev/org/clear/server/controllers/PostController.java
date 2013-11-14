package org.clear.server.controllers;

import java.util.HashSet;

import javax.jdo.PersistenceManager;

import org.clear.server.data.Friends;
import org.clear.server.data.PMF;
import org.clear.server.data.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

	private static final String JSON_VIEW = "jsonView";

	// show the content of the post
	@RequestMapping(value = "/data/posts/show", method = RequestMethod.GET)
	public String showPost(@RequestParam("lat") String lat,
			@RequestParam("lon") String lon, @RequestParam("time") String time,
			Model model) {

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Post post = Post.byPlaceAndTime(lat, lon, time, pm);

		if (post == null) {
			model.addAttribute("data", null);
		}else{
			model.addAttribute("data",post);
		}
		post.save(pm);

		pm.close();
		return JSON_VIEW;
	}

	// add post or add request under the post
	@RequestMapping(value = "/data/posts/add", method = RequestMethod.GET)
	public String addPost(@RequestParam("user") String user,
			@RequestParam("lat") String lat, @RequestParam("lon") String lon,
			@RequestParam("time") String time, Model model) {

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Post post = Post.byPlaceAndTime(lat, lon, time, pm);

		if (post == null) {
			post = new Post();
			post.setTime(time);
			post.setLat(lat);
			post.setLon(lon);
		}
		post.addUser(user);
		post.save(pm);

		pm.close();

		model.addAttribute("data", true);

		return JSON_VIEW;
	}
}
