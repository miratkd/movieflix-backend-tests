package com.devsuperior.movieflix.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional(readOnly = true)
	public Page<MovieDTO> findAllPaged(Long genreId, PageRequest pageRequest){
		if(genreId == 0)
		{
			Page<Movie> list = repository.findAll(pageRequest);
			return list.map(x -> new MovieDTO(x));
		}
		else 
		{
			Genre categories = (genreId == 0) ? null : genreRepository.getOne(genreId);
			Page<Movie> list = repository.find(categories, pageRequest);
			return list.map(x -> new MovieDTO(x));
		}
	}
	
	@Transactional(readOnly = true)
	public MovieDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		return new MovieDTO(obj.orElseThrow());
	}
}
