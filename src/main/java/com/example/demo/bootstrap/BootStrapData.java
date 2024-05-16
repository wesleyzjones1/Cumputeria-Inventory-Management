package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Product;
import com.example.demo.reports.ReportGenerator;
import com.example.demo.repositories.InhousePartRepository;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    private final OutsourcedPartRepository outsourcedPartRepository;

@Autowired
    private InhousePartRepository inhousePartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

//        partRepository.deleteAll();
//        productRepository.deleteAll();
//        outsourcedPartRepository.deleteAll();
//        System.out.println("Deleted");

        //check if there are any loaded parts
        if (partRepository.count() == 0 && productRepository.count() == 0) {

            InhousePart gpu1= new InhousePart();
            gpu1.setPartId(1);
            gpu1.setName("RTX 3050");
            gpu1.setPrice(249.99);
            gpu1.setInv(12);
            gpu1.setMinInventory(5);
            gpu1.setMaxInventory(50);
            gpu1.setDate("03/22/2024");
            gpu1.setStoreNumber(1);

            InhousePart gpu2 = new InhousePart();
            gpu2.setPartId(2);
            gpu2.setName("RTX 3060");
            gpu2.setPrice(399.99);
            gpu2.setInv(31);
            gpu2.setMinInventory(5);
            gpu2.setMaxInventory(50);
            gpu2.setDate("03/28/2024");
            gpu2.setStoreNumber(2);

            InhousePart gpu3 = new InhousePart();
            gpu3.setPartId(3);
            gpu3.setName("RTX 3070");
            gpu3.setPrice(499.99);
            gpu3.setInv(42);
            gpu3.setMinInventory(5);
            gpu3.setMaxInventory(50);
            gpu3.setDate("04/01/2024");
            gpu3.setStoreNumber(3);

            InhousePart gpu4 = new InhousePart();
            gpu4.setPartId(4);
            gpu4.setName("RTX 3080");
            gpu4.setPrice(699.99);
            gpu4.setInv(31);
            gpu4.setMinInventory(5);
            gpu4.setMaxInventory(50);
            gpu4.setDate("03/25/2024");
            gpu4.setStoreNumber(4);

            OutsourcedPart gpu5 = new OutsourcedPart();
            gpu5.setId(5);
            gpu5.setName("RTX 3090");
            gpu5.setPrice(899.99);
            gpu5.setInv(12);
            gpu5.setCompanyName("NVDIA");
            gpu5.setMinInventory(5);
            gpu5.setMaxInventory(50);
            gpu5.setDate("03/25/2024");
            gpu5.setStoreNumber(3);

            OutsourcedPart thePart=null;
            List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
            for(OutsourcedPart part:outsourcedParts){
                if(part.getName().equals("out test"))thePart=part;
            }

            partRepository.save(gpu1);
            partRepository.save(gpu2);
            partRepository.save(gpu3);
            partRepository.save(gpu4);
            outsourcedPartRepository.save(gpu5);

            Product Home_PC= new Product(1,"Home PC",259.99,24, "04/25/2024", 12);

            Product Office_PC= new Product(2,"Office PC",599.99,22, "04/26/2024", 2);

            Product Gaming_PC_1= new Product(3,"Gaming PC 1",799.99,19, "04/27/2024", 11);

            Product Gaming_PC_2= new Product(4,"Gaming PC 2",999.99,13, "04/28/2024", 4);

            Product Gaming_PC_3= new Product(5,"Gaming PC 3",1399.99,6, "04/29/2024",5);

            productRepository.save(Home_PC);
            productRepository.save(Office_PC);
            productRepository.save(Gaming_PC_1);
            productRepository.save(Gaming_PC_2);
            productRepository.save(Gaming_PC_3);
            }
       /*

        o.setCompanyName("Western Governors University");
        o.setName("out test");
        o.setInv(5);
        o.setPrice(20.0);
        o.setId(100L);
        outsourcedPartRepository.save(o);
        OutsourcedPart thePart=null;
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            if(part.getName().equals("out test"))thePart=part;
        }

        System.out.println(thePart.getCompanyName());
        */

        List<InhousePart> inhouseParts = (List<InhousePart>) inhousePartRepository.findAll();

        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            System.out.println(part.getName()+" "+part.getCompanyName());
        }

        List<Product> products = (List<Product>) productRepository.findAll();


        ReportGenerator pdfGenerator = new ReportGenerator();
        System.out.println("Creating Record");
        pdfGenerator.generateTextFile(inhouseParts, outsourcedParts, products);

        System.out.println("Started in Bootstrap");
        System.out.println("Number of Products"+productRepository.count());
        System.out.println(productRepository.findAll());
        System.out.println("Number of Parts"+partRepository.count());
        System.out.println(partRepository.findAll());

    }
}
