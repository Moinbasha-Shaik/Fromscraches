//package in.basha.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import in.basha.entity.Document;
//import in.basha.repo.DocumentRepository;
//
//@Service
//public class DocumentService {
//
//	@Autowired
//	private DocumentRepository documentRepository;
//
//	@Autowired
//	private S3Service s3Service;
//
//	public Document saveDocument(String title, String description, MultipartFile file) throws Exception {
//
//		Document doc = new Document();
//		doc.setTitle(title);
//		doc.setDescription(description);
//
//		Document savedDoc = documentRepository.save(doc);
//
//		String fileName = savedDoc.getId() + ".pdf";
//		String fileUrl = s3Service.uploadFile(file, fileName);
//
//		savedDoc.setFileUrl(fileUrl);
//		return documentRepository.save(savedDoc);
//	}
//
//	public Optional<Document> getDocument(Long id) {
//		return documentRepository.findById(id);
//	}
//}

package in.basha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.basha.entity.Document;
import in.basha.repo.DocumentRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private S3Service s3Service;

	public Document saveDocument(String name, String email, String company, String title, String description,
			String phoneNumber, MultipartFile file) throws Exception {

		Document doc = new Document();
		doc.setName(name);
		doc.setEmail(email);
		doc.setCompany(company);
		doc.setTitle(title);
		doc.setDescription(description);
		doc.setPhoneNumber(phoneNumber);

		Document savedDoc = documentRepository.save(doc);

		if (file != null && !file.isEmpty()) {
			String fileName = savedDoc.getId() + ".pdf";
			String fileUrl = s3Service.uploadFile(file, fileName);
			savedDoc.setFileUrl(fileUrl);
			savedDoc = documentRepository.save(savedDoc);
		}

		return savedDoc;
	}

	public List<Document> getAllDocuments() {
		try {
			return documentRepository.findAll();
		} catch (Exception e) {
			System.err.println("Error retrieving documents: " + e.getMessage());
			throw new RuntimeException("Failed to retrieve documents", e);
		}
	}

}
