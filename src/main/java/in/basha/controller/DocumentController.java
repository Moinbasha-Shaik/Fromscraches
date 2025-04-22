//package in.basha.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import in.basha.entity.Document;
//import in.basha.repo.DocumentRepository;
//import in.basha.service.S3Service;
//
//@RestController
//@RequestMapping("/api/documents")
//public class DocumentController {
//
//	@Autowired
//	private S3Service service;
//
//	@Autowired
//	private DocumentRepository documentRepository;
//
//	@PostMapping("/upload")
//	public ResponseEntity<String> uploadPdf(@RequestParam("title") String title,
//			@RequestParam("description") String description,
//			@RequestParam(value = "file", required = false) MultipartFile file) {
//		try {
//			Document document = new Document();
//			document.setTitle(title);
//			document.setDescription(description);
//
//			document = documentRepository.save(document); // Save first to get generatedId
//
//			if (file != null && !file.isEmpty()) {
//				String fileName = document.getId() + ".pdf";
//				String fileUrl = service.uploadFile(file, fileName);
//				document.setFileUrl(fileUrl);
//				documentRepository.save(document); // Update with file URL
//			}
//
//			return ResponseEntity.ok("Document saved with ID: " + document.getId());
//		} catch (Exception e) {
//			return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
//		}
//	}
//}





package in.basha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.basha.entity.Document;
import in.basha.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("company") String company,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Document document = documentService.saveDocument(name, email, company, title, description,phoneNumber, file);
            return ResponseEntity.ok("Document saved with ID: " + document.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            List<Document> documents = documentService.getAllDocuments();
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch documents: " + e.getMessage());
        }
    }

}
