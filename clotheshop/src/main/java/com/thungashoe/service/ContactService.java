package com.thungashoe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.thungashoe.domain.entity.Contact;
import com.thungashoe.repository.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;

	public void saveContact(Contact contact) {
		contactRepository.save(contact);
	}

	public Page<Contact> getAllContacts(int page, int size, String sortBy, String sortDirection) {
		Pageable pageable;
		if ((sortBy == null || sortBy.isEmpty()) && (sortDirection == null || sortDirection.isEmpty())) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			pageable = PageRequest.of(page, size, sort);
		}
		return contactRepository.findAll(pageable);
	}

	public Contact getContactById(Long contactId) {
		return contactRepository.findById(contactId).orElse(null);
	}

	public void addContact(Contact contact) {
		contactRepository.save(contact);
	}

	public void deleteContact(Long contactId) {
		Contact contact = getContactById(contactId);
		if (contact != null) {
			contactRepository.delete(contact);
		}
	}
}
