package edu.esa;

import java.io.File;
import java.io.IOException;
import java.util.*;

import edu.esa.core.errors.ErrorsData;
import edu.esa.core.facade.ESAFacade;
import edu.esa.core.facade.ErrorSearchOptions;
import edu.esa.core.facade.ErrorType;
import edu.esa.core.parsers.KnowledgeBaseSource;
import edu.esa.core.parsers.SourceType;
import edu.esa.core.parsers.exceptions.KnowledgeBaseParseException;
import edu.esa.core.parsers.factory.FactoryCreator;
import edu.esa.data.ErrorReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WelcomeController {

    // inject via application.properties
    @Value("${welcome.message:test}")
    private String message = "Hello World";

    @GetMapping("/")
    public String index(ModelMap model) {
        model.clear();
        return "index";
    }

    @PostMapping("/")
    public String upload(@RequestParam("file") MultipartFile file,
                         RedirectAttributes redirectAttributes, ModelMap model) {

        String filePath = "c:/xmlFiles/"+file.getName().hashCode()+".xml";

        try {
            File target = new File(filePath);
            if(target.exists()) {
                target.delete();
            }
            target.createNewFile();
            file.transferTo(target);
            List<ErrorReport> reports = analyze(filePath);
            model.addAttribute("reports", reports);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Невозможно загрузить файл. Попробуйте снова");
            e.printStackTrace();
        } catch (KnowledgeBaseParseException e) {
            model.addAttribute("errorMessage", "Невозможно проанализировать файл. " +
                    "Попробуйте снова");
            e.printStackTrace();
        }

        model.addAttribute("chosenFile", file.getOriginalFilename());
        model.addAttribute("filename", file.getOriginalFilename());

        return "index";
    }

    private List<ErrorReport> analyze(String fileName) throws KnowledgeBaseParseException {
        KnowledgeBaseSource source =
                FactoryCreator.getParsingFactory(SourceType.XML).buildKnowledgeBaseSourse(fileName);

        ErrorSearchOptions options = buildOptions();
        ErrorsData errorsData = ESAFacade.searchErrors(source, options);
        List<ErrorReport> reports = buildReports(options, errorsData);
        return reports;
    }

    private ErrorSearchOptions buildOptions(){
        ErrorSearchOptions options = new ErrorSearchOptions();
        options.searchExplicitInsignificatChain = true;
        options.searchImplicitInsignificantChain = true;
        options.searchInclusiveDuplicates = true;
        options.searchCompleteDuplicates = true;
        options.searchPartialDuplicates = true;
        options.searchRedundantChains = true;
        options.searchRedundantForInput = true;
        options.searchRedundantForOutput = true;
        options.searchCycles = true;
        options.searchSimpleCycles = true;
        options.searchIsolatedVertices = true;
        options.searchContradictingChains = true;
        return options;
    }

    private List<ErrorReport> buildReports(ErrorSearchOptions options, ErrorsData errorsData){
        List<ErrorReport> reports = new ArrayList<>();

        ErrorType type;
        String message;
        String recommendations;
        List<String> points;

        if(options.searchCompleteDuplicates) {
            type = ErrorType.COMPLETE_DUPLICATES;
            if(errorsData.getCompleteDuplicates().isEmpty()) {
                message = "Полные дубликаты не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Группы идентификаторов полных дубликатов:";
                points = getComplexPoints(errorsData.getCompleteDuplicates());
                recommendations = "В данном случае в каждой группе дубликатов рекомендуется " +
                        "оставить по одному правилу, остальные - удалить";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Полные дубликаты"));
        }

        if(options.searchPartialDuplicates) {
            type = ErrorType.PARTIAL_DUPLICATES;
            if(errorsData.getPartialDuplicates().isEmpty()) {
                message = "Неполные дубликаты не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Группы идентификаторов неполных дубликатов:";
                points = getComplexPoints(errorsData.getPartialDuplicates());
                recommendations = "В данном случае стоит рассмотреть каждую группу " +
                        "дубликатов и принять решение об удалении или замене правил в каждом отдельном случае";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Неполные дубликаты"));
        }

        if(options.searchInclusiveDuplicates) {
            type = ErrorType.INCLUSIVE_DUPLICATES;
            if(errorsData.getInclusiveDuplicates().isEmpty()) {
                message = "Включающие дубликаты не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Группы идентификаторов включающих дубликатов:";
                points = getComplexPoints(errorsData.getInclusiveDuplicates());
                recommendations = "Рекомендуется в каждой группе оставить " +
                        "правила с меньшим количеством входных фактов";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Включающие дубликаты"));
        }

        if(options.searchExplicitInsignificatChain) {
            type = ErrorType.EXPLICIT_INSIGNIFICANT_CHAINS;
            if(errorsData.getExplicitInsignificantChains().isEmpty()) {
                message = "Явные незначащие цепочки вывода не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Явные незначащие цепочки вывода:";
                points = getComplexPointsForList(errorsData.getExplicitInsignificantChains());
                recommendations = "Рекомендуется удалить указанные цепочки вывода";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Явные незначащие цепочки вывода"));
        }

        if(options.searchImplicitInsignificantChain) {
            type = ErrorType.IMPLICIT_INSIGNIFICANT_CHAINS;
            if(errorsData.getImplicitInsignificantChains().isEmpty()) {
                message = "Невные незначащие цепочки вывода не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Неявные незначащие цепочки вывода:";
                points = getComplexPointsForList(errorsData.getImplicitInsignificantChains());
                recommendations = "Рекомендуется удалить указанные цепочки вывода, " +
                        "заменив их единичными правилами";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Неявные незначащие цепочки вывода"));
        }

        if(options.searchRedundantChains) {
            type = ErrorType.REDUNDANT_CHAINS;
            if(errorsData.getRedundantChains().isEmpty()) {
                message = "Избыточные цепочки вывода не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Избыточные цепочки вывода:";
                points = getComplexPointsForList(errorsData.getRedundantChains());
                recommendations = "Рекомендуется удалить указанные цепочки вывода, " +
                        "либо дополнить их правилами, ведущими к цели";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Избыточные цепочки вывода"));
        }

        if(options.searchRedundantForInput) {
            type = ErrorType.REDUNDANT_FOR_INPUT;
            if(errorsData.getRedundantForInputRules().isEmpty()) {
                message = "Правила, избыточные по входу, не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Правила, избыточные по входу:";
                points = new ArrayList<>(errorsData.getRedundantForInputRules());
                recommendations = "Рекомендуется рассмотреть приведенные правила и возможность их удаления";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Правила, избыточные по входу"));
        }

        if(options.searchRedundantForOutput) {
            type = ErrorType.REDUNDANT_FOR_OUTPUT;
            if(errorsData.getRedundantForOutputRules().isEmpty()) {
                message = "Правила, избыточные по выходу, не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Правила, избыточные по выходу:";
                points = new ArrayList<>(errorsData.getRedundantForOutputRules());
                recommendations = "Рекомендуется удалить указанные правила";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Правила, избыточные по выходу"));
        }

        if(options.searchCycles) {
            type = ErrorType.CYCLE_CHAINS;
            if(errorsData.getCycles().isEmpty()) {
                message = "Циклические цепочки вывода не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Циклические цепочки вывода:";
                points = getComplexPointsForList(errorsData.getCycles());
                recommendations = "Рекомендуется удалить одно или несколько правил из указанных цепочек вывода";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Циклические цепочки вывода"));
        }

        if(options.searchSimpleCycles) {
            type = ErrorType.SIMPLE_CYCLES;
            if(errorsData.getSimpleCycles().isEmpty()) {
                message = "Простые циклы не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Простые циклы:";
                points = new ArrayList<>(errorsData.getSimpleCycles());
                recommendations = "Рекомендуется удалить перечисленные правила";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Простые циклы"));
        }

        if(options.searchIsolatedVertices) {
            type = ErrorType.ISOLATED_VERTICES;
            if(errorsData.getIsolatedVertices().isEmpty()) {
                message = "Изолированные вершины не найдены";
                points = null;
                recommendations = null;
            } else {
                message = "Изолированные вершины:";
                points = new ArrayList<>(errorsData.getIsolatedVertices());
                recommendations = "Рекомендуется создать правила, ведущие к вершинам, " +
                        "или рассмотреть возможность их удаления";
            }
            reports.add(new ErrorReport(type, message, recommendations, points, "Изолированные вершины"));
        }

        return reports;
    }

    private List<String> getComplexPoints(Collection<Collection<String>> rules){
        List<String> points = new ArrayList<>();

        for(Collection<String> rulesList : rules) {
            if(rulesList.isEmpty()) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = rulesList.iterator();
            while(it.hasNext()) {
                sb.append(it.next());
                if(it.hasNext()) {
                    sb.append(", ");
                }
            }
            points.add(sb.toString());
        }
        return points;
    }

    private List<String> getComplexPointsForList(Collection<List<String>> rules){
        List<String> points = new ArrayList<>();

        for(Collection<String> rulesList : rules) {
            if(rulesList.isEmpty()) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = rulesList.iterator();
            while(it.hasNext()) {
                sb.append(it.next());
                if(it.hasNext()) {
                    sb.append(", ");
                }
            }
            points.add(sb.toString());
        }
        return points;
    }
}
