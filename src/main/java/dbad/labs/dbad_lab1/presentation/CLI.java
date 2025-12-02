package dbad.labs.dbad_lab1.presentation;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.domain.dtos.*;
import dbad.labs.dbad_lab1.domain.services.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class CLI {

    private final DepartmentService departmentService;
    private final ShopService shopService;
    private final ProductService productService;
    private final PassportService passportService;
    private final EmployeeService employeeService;
    private final SaleService saleService;
    private final InventoryService inventoryService;

    private final Scanner scanner;

    @Inject
    public CLI(DepartmentService departmentService, ShopService shopService,
               ProductService productService, PassportService passportService,
               EmployeeService employeeService, SaleService saleService,
               InventoryService inventoryService) {
        this.departmentService = departmentService;
        this.shopService = shopService;
        this.productService = productService;
        this.passportService = passportService;
        this.employeeService = employeeService;
        this.saleService = saleService;
        this.inventoryService = inventoryService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            clearScreen();
            System.out.println("=== ГЛАВНОЕ МЕНЮ ===");
            System.out.println("1. Магазины");
            System.out.println("2. Продукты");
            System.out.println("3. Склады");
            System.out.println("4. Паспортные столы");
            System.out.println("5. Паспорта");
            System.out.println("6. Сотрудники");
            System.out.println("7. Продажи");
            System.out.println("0. Выход");
            System.out.print("\nВыберите пункт: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> handleShops();
                    case "2" -> handleProducts();
                    case "3" -> handleInventory();
                    case "4" -> handleDepartments();
                    case "5" -> handlePassports();
                    case "6" -> handleEmployees();
                    case "7" -> handleSales();
                    case "0" -> {
                        return;
                    }
                    default -> showMessage("Неверный ввод.");
                }
            } catch (Exception e) {
                showMessage("Критическая ошибка: " + e.getMessage());
            }
        }
    }

    private void handleDepartments() {
        while (true) {
            clearScreen();
            System.out.println("--- Управление Паспортными столами ---");
            System.out.println("1. Список всех");
            System.out.println("2. Поиск");
            System.out.println("3. Добавить");
            System.out.println("4. Редактировать");
            System.out.println("5. Удалить");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String op = scanner.nextLine();
            if (op.equals("0")) return;

            try {
                switch (op) {
                    case "1" -> printDepartments(departmentService.findAll());
                    case "2" -> {
                        System.out.print("Поиск: ");
                        printDepartments(departmentService.search(scanner.nextLine()));
                    }
                    case "3" -> {
                        DepartmentDTO dto = new DepartmentDTO();
                        System.out.print("Название: ");
                        dto.setDepartName(scanner.nextLine());
                        departmentService.create(dto);
                        System.out.println("Успешно создан!");
                    }
                    case "4" -> {
                        long id = selectEntity(departmentService.findAll(), DepartmentDTO::getDepartName, DepartmentDTO::getId, "Отдел для редактирования");
                        if (id == 0) break;
                        DepartmentDTO dto = new DepartmentDTO();
                        dto.setId(id);
                        System.out.print("Новое название: ");
                        dto.setDepartName(scanner.nextLine());
                        departmentService.update(dto);
                        System.out.println("Обновлено.");
                    }
                    case "5" -> {
                        long id = selectEntity(departmentService.findAll(), DepartmentDTO::getDepartName, DepartmentDTO::getId, "Отдел для удаления");
                        if (id == 0) break;
                        DepartmentDTO dto = new DepartmentDTO();
                        dto.setId(id);
                        departmentService.delete(dto);
                        System.out.println("Удален.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            waitEnter();
        }
    }

    private void printDepartments(List<DepartmentDTO> list) {
        if (list.isEmpty()) { System.out.println("Список пуст."); return; }
        System.out.printf("| %-5s | %-30s |%n", "ID", "Название");
        System.out.println("-".repeat(40));
        for (DepartmentDTO d : list) System.out.printf("| %-5d | %-30s |%n", d.getId(), d.getDepartName());
    }

    private void handleShops() {
        while (true) {
            clearScreen();
            System.out.println("--- Управление Магазинами ---");
            System.out.println("1. Список всех");
            System.out.println("2. Поиск");
            System.out.println("3. Добавить");
            System.out.println("4. Редактировать");
            System.out.println("5. Удалить");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String op = scanner.nextLine();
            if (op.equals("0")) return;

            try {
                switch (op) {
                    case "1" -> printShops(shopService.findAll());
                    case "2" -> {
                        System.out.print("Поиск: ");
                        printShops(shopService.search(scanner.nextLine()));
                    }
                    case "3" -> {
                        ShopDTO dto = new ShopDTO();
                        fillShop(dto);
                        shopService.create(dto);
                        System.out.println("Магазин создан.");
                    }
                    case "4" -> {
                        long id = selectEntity(shopService.findAll(), ShopDTO::getShopName, ShopDTO::getId, "Магазин для редактирования");
                        if (id == 0) break;
                        ShopDTO dto = new ShopDTO();
                        dto.setId(id);
                        fillShop(dto);
                        shopService.update(dto);
                        System.out.println("Магазин обновлен.");
                    }
                    case "5" -> {
                        long id = selectEntity(shopService.findAll(), ShopDTO::getShopName, ShopDTO::getId, "Магазин для удаления");
                        if (id == 0) break;
                        ShopDTO dto = new ShopDTO();
                        dto.setId(id);
                        shopService.delete(dto);
                        System.out.println("Магазин удален.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            waitEnter();
        }
    }

    private void fillShop(ShopDTO dto) {
        System.out.print("Название: ");
        dto.setShopName(scanner.nextLine());
        System.out.print("Адрес: ");
        dto.setAddress(scanner.nextLine());
    }

    private void printShops(List<ShopDTO> list) {
        if (list.isEmpty()) { System.out.println("Список пуст."); return; }
        System.out.printf("| %-5s | %-20s | %-30s |%n", "ID", "Название", "Адрес");
        System.out.println("-".repeat(65));
        for (ShopDTO s : list) System.out.printf("| %-5d | %-20s | %-30s |%n", s.getId(), s.getShopName(), s.getAddress());
    }

    private void handleProducts() {
        while (true) {
            clearScreen();
            System.out.println("--- Управление Продуктами ---");
            System.out.println("1. Список всех");
            System.out.println("2. Поиск");
            System.out.println("3. Добавить");
            System.out.println("4. Редактировать");
            System.out.println("5. Удалить");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String op = scanner.nextLine();
            if (op.equals("0")) return;

            try {
                switch (op) {
                    case "1" -> printProducts(productService.findAll());
                    case "2" -> {
                        System.out.print("Поиск: ");
                        printProducts(productService.search(scanner.nextLine()));
                    }
                    case "3" -> {
                        ProductDTO dto = new ProductDTO();
                        fillProduct(dto);
                        productService.create(dto);
                        System.out.println("Продукт создан.");
                    }
                    case "4" -> {
                        long id = selectEntity(productService.findAll(), ProductDTO::getProdName, ProductDTO::getId, "Продукт для редактирования");
                        if (id == 0) break;
                        ProductDTO dto = new ProductDTO();
                        dto.setId(id);
                        fillProduct(dto);
                        productService.update(dto);
                        System.out.println("Продукт обновлен.");
                    }
                    case "5" -> {
                        long id = selectEntity(productService.findAll(), ProductDTO::getProdName, ProductDTO::getId, "Продукт для удаления");
                        if (id == 0) break;
                        ProductDTO dto = new ProductDTO();
                        dto.setId(id);
                        productService.delete(dto);
                        System.out.println("Продукт удален.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            waitEnter();
        }
    }

    private void fillProduct(ProductDTO dto) {
        System.out.print("Название: ");
        dto.setProdName(scanner.nextLine());
        System.out.print("Ед. измерения: ");
        dto.setUnit(scanner.nextLine());
    }

    private void printProducts(List<ProductDTO> list) {
        if (list.isEmpty()) { System.out.println("Список пуст."); return; }
        System.out.printf("| %-5s | %-20s | %-10s |%n", "ID", "Название", "Ед.изм.");
        System.out.println("-".repeat(45));
        for (ProductDTO p : list) System.out.printf("| %-5d | %-20s | %-10s |%n", p.getId(), p.getProdName(), p.getUnit());
    }

    private void handlePassports() {
        while (true) {
            clearScreen();
            System.out.println("--- Управление Паспортами ---");
            System.out.println("1. Список всех");
            System.out.println("2. Поиск");
            System.out.println("3. Добавить");
            System.out.println("4. Редактировать");
            System.out.println("5. Удалить");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String op = scanner.nextLine();
            if (op.equals("0")) return;

            try {
                switch (op) {
                    case "1" -> printPassports(passportService.findAll());
                    case "2" -> {
                        System.out.print("Поиск: ");
                        printPassports(passportService.search(scanner.nextLine()));
                    }
                    case "3" -> {
                        PassportDTO dto = new PassportDTO();
                        fillPassport(dto);
                        passportService.create(dto);
                        System.out.println("Паспорт создан.");
                    }
                    case "4" -> {
                        long id = selectEntity(passportService.findAll(), PassportDTO::getPassportNum, PassportDTO::getId, "Паспорт для редактирования");
                        if (id == 0) break;
                        PassportDTO dto = new PassportDTO();
                        dto.setId(id);
                        fillPassport(dto);
                        passportService.update(dto);
                        System.out.println("Паспорт обновлен.");
                    }
                    case "5" -> {
                        long id = selectEntity(passportService.findAll(), PassportDTO::getPassportNum, PassportDTO::getId, "Паспорт для удаления");
                        if (id == 0) break;
                        PassportDTO dto = new PassportDTO();
                        dto.setId(id);
                        passportService.delete(dto);
                        System.out.println("Паспорт удален.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            waitEnter();
        }
    }

    private void fillPassport(PassportDTO dto) {
        System.out.print("Номер паспорта: ");
        dto.setPassportNum(scanner.nextLine());
        System.out.print("Дата выдачи (YYYY-MM-DD): ");
        try {
            dto.setIssueDate(LocalDate.parse(scanner.nextLine()));
        } catch (DateTimeParseException e) {
            System.out.println("Неверная дата, пропуск.");
        }

        long deptId = selectEntity(
                departmentService.findAll(),
                DepartmentDTO::getDepartName,
                DepartmentDTO::getId,
                "Отдел выдачи"
        );
        dto.setDepartmentId(deptId);
    }

    private void printPassports(List<PassportDTO> list) {
        if (list.isEmpty()) { System.out.println("Список пуст."); return; }
        System.out.printf("| %-5s | %-15s | %-12s | %-20s |%n", "ID", "Номер", "Дата", "Отдел");
        System.out.println("-".repeat(60));
        for (PassportDTO p : list)
            System.out.printf("| %-5d | %-15s | %-12s | %-20s |%n",
                    p.getId(), p.getPassportNum(), p.getIssueDate(), p.getDepartmentName());
    }

    private void handleEmployees() {
        while (true) {
            clearScreen();
            System.out.println("--- Управление Сотрудниками ---");
            System.out.println("1. Список всех");
            System.out.println("2. Поиск");
            System.out.println("3. Добавить");
            System.out.println("4. Редактировать");
            System.out.println("5. Удалить");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String op = scanner.nextLine();
            if (op.equals("0")) return;

            try {
                switch (op) {
                    case "1" -> printEmployees(employeeService.findAll());
                    case "2" -> {
                        System.out.print("Поиск: ");
                        printEmployees(employeeService.search(scanner.nextLine()));
                    }
                    case "3" -> {
                        EmployeeDTO dto = new EmployeeDTO();
                        fillEmployee(dto);
                        employeeService.create(dto);
                        System.out.println("Сотрудник добавлен.");
                    }
                    case "4" -> {
                        long id = selectEntity(employeeService.findAll(), e -> e.getLastName() + " " + e.getFirstName(), EmployeeDTO::getId, "Сотрудника для редактирования");
                        if (id == 0) break;
                        EmployeeDTO dto = new EmployeeDTO();
                        dto.setId(id);
                        fillEmployee(dto);
                        employeeService.update(dto);
                        System.out.println("Сотрудник обновлен.");
                    }
                    case "5" -> {
                        long id = selectEntity(employeeService.findAll(), e -> e.getLastName() + " " + e.getFirstName(), EmployeeDTO::getId, "Сотрудника для удаления");
                        if (id == 0) break;
                        EmployeeDTO dto = new EmployeeDTO();
                        dto.setId(id);
                        employeeService.delete(dto);
                        System.out.println("Сотрудник удален.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            waitEnter();
        }
    }

    private void fillEmployee(EmployeeDTO dto) {
        System.out.print("Фамилия: ");
        dto.setLastName(scanner.nextLine());
        System.out.print("Имя: ");
        dto.setFirstName(scanner.nextLine());
        System.out.print("Отчество: ");
        dto.setMiddleName(scanner.nextLine());
        System.out.print("Адрес: ");
        dto.setAddress(scanner.nextLine());
        System.out.print("Дата рождения (YYYY-MM-DD): ");
        try {
            dto.setBirthDate(LocalDate.parse(scanner.nextLine()));
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка даты, будет пустой.");
        }

        dto.setShopId(selectEntity(
                shopService.findAll(),
                ShopDTO::getShopName,
                ShopDTO::getId,
                "Магазин"
        ));

        dto.setPassportId(selectEntity(
                passportService.findAll(),
                PassportDTO::getPassportNum,
                PassportDTO::getId,
                "Паспорт"
        ));
    }

    private void printEmployees(List<EmployeeDTO> list) {
        if (list.isEmpty()) { System.out.println("Список пуст."); return; }
        System.out.printf("| %-5s | %-25s | %-20s | %-15s |%n", "ID", "Фамилия Имя", "Магазин", "Паспорт");
        System.out.println("-".repeat(75));
        for (EmployeeDTO e : list)
            System.out.printf("| %-5d | %-25s | %-20s | %-15s |%n",
                    e.getId(),
                    e.getLastName() + " " + e.getFirstName(),
                    e.getShopName() == null ? "-" : e.getShopName(),
                    e.getPassportNum() == null ? "-" : e.getPassportNum());
    }

    private void handleSales() {
        while (true) {
            clearScreen();
            System.out.println("--- Управление Продажами ---");
            System.out.println("1. Список всех");
            System.out.println("2. Поиск");
            System.out.println("3. Добавить");
            System.out.println("4. Редактировать");
            System.out.println("5. Удалить");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String op = scanner.nextLine();
            if (op.equals("0")) return;

            try {
                switch (op) {
                    case "1" -> printSales(saleService.findAll());
                    case "2" -> {
                        System.out.print("Поиск: ");
                        printSales(saleService.search(scanner.nextLine()));
                    }
                    case "3" -> {
                        SaleDTO dto = new SaleDTO();
                        // true = это новая продажа, надо списать со склада
                        if (fillSale(dto, true)) {
                            saleService.create(dto);
                            System.out.println("Продажа зарегистрирована.");
                        }
                    }
                    case "4" -> {
                        System.out.print("Введите ID продажи для редактирования: ");
                        long id = readLong();
                        SaleDTO dto = new SaleDTO();
                        dto.setId(id);
                        if (fillSale(dto, false)) {
                            saleService.update(dto);
                            System.out.println("Продажа обновлена.");
                        }
                    }
                    case "5" -> {
                        System.out.print("Введите ID продажи для удаления: ");
                        SaleDTO dto = new SaleDTO();
                        dto.setId(readLong());
                        saleService.delete(dto);
                        System.out.println("Продажа удалена.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            waitEnter();
        }
    }

    private boolean fillSale(SaleDTO dto, boolean isNewSale) {
        System.out.print("Дата и время (YYYY-MM-DDTHH:MM, Enter=Сейчас): ");
        String dateStr = scanner.nextLine();
        if (dateStr.isBlank()) {
            dto.setSaleDateTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC));
        } else {
            try {
                dto.setSaleDateTimestamp(LocalDateTime.parse(dateStr).atOffset(ZoneOffset.UTC));
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат, установлено текущее время.");
                dto.setSaleDateTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC));
            }
        }

        long shopId = selectEntity(
                shopService.findAll(),
                ShopDTO::getShopName,
                ShopDTO::getId,
                "Магазин"
        );
        if (shopId == 0) return false;
        dto.setShopId(shopId);

        long productId = selectEntity(
                productService.findAll(),
                ProductDTO::getProdName,
                ProductDTO::getId,
                "Продукт"
        );
        if (productId == 0) return false;
        dto.setProductId(productId);

        InventoryDTO inventoryItem = inventoryService.findAll().stream()
                .filter(i -> i.getShopId() == shopId && i.getProductId() == productId)
                .findFirst()
                .orElse(null);

        if (inventoryItem == null) {
            System.out.println("ОШИБКА: Этого товара нет на складе выбранного магазина!");
            return false;
        }

        BigDecimal available = inventoryItem.getQuantity();
        System.out.println(">>> Доступно на складе: " + available);

        BigDecimal quantitySold;
        while (true) {
            System.out.print("Введите количество для продажи: ");
            try {
                String qtyStr = scanner.nextLine();
                quantitySold = new BigDecimal(qtyStr);

                if (quantitySold.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Количество должно быть больше 0.");
                    continue;
                }

                if (isNewSale && quantitySold.compareTo(available) > 0) {
                    System.out.println("Ошибка: Недостаточно товара на складе! Максимум: " + available);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите число.");
            }
        }
        dto.setQuantitySold(quantitySold);

        if (isNewSale) {
            inventoryItem.setQuantity(available.subtract(quantitySold));
            inventoryService.update(inventoryItem);
            System.out.println(">>> Склад обновлен. Остаток: " + inventoryItem.getQuantity());
        }

        long empId = selectEntity(
                employeeService.findAll(),
                e -> e.getLastName() + " " + e.getFirstName(),
                EmployeeDTO::getId,
                "Продавца"
        );
        if (empId == 0) return false;
        dto.setEmployeeId(empId);

        return true;
    }

    private void printSales(List<SaleDTO> list) {
        if (list.isEmpty()) { System.out.println("Список пуст."); return; }
        System.out.printf("| %-5s | %-16s | %-20s | %-15s | %-10s |%n", "ID", "Дата", "Товар", "Продавец", "Кол-во");
        System.out.println("-".repeat(80));
        for (SaleDTO s : list)
            System.out.printf("| %-5d | %-16s | %-20s | %-15s | %-10s |%n",
                    s.getId(),
                    s.getSaleDateTimestamp().toLocalDate(),
                    s.getProductName(),
                    s.getEmployeeFullName(),
                    s.getQuantitySold());
    }

    private void handleInventory() {
        while (true) {
            clearScreen();
            System.out.println("--- Управление Складом ---");
            System.out.println("1. Список всех");
            System.out.println("2. Поиск");
            System.out.println("3. Добавить");
            System.out.println("4. Редактировать");
            System.out.println("5. Удалить");
            System.out.println("0. Назад");
            System.out.print("\nВаш выбор: ");

            String op = scanner.nextLine();
            if (op.equals("0")) return;

            try {
                switch (op) {
                    case "1" -> printInventory(inventoryService.findAll());
                    case "2" -> {
                        System.out.print("Поиск: ");
                        printInventory(inventoryService.search(scanner.nextLine()));
                    }
                    case "3" -> {
                        InventoryDTO dto = new InventoryDTO();
                        fillInventory(dto);
                        inventoryService.create(dto);
                        System.out.println("Запись добавлена.");
                    }
                    case "4" -> {
                        System.out.print("Введите ID записи для редактирования: ");
                        long id = readLong();
                        InventoryDTO dto = new InventoryDTO();
                        dto.setId(id);
                        fillInventory(dto);
                        inventoryService.update(dto);
                        System.out.println("Запись обновлена.");
                    }
                    case "5" -> {
                        System.out.print("Введите ID записи для удаления: ");
                        InventoryDTO dto = new InventoryDTO();
                        dto.setId(readLong());
                        inventoryService.delete(dto);
                        System.out.println("Запись удалена.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            waitEnter();
        }
    }

    private void fillInventory(InventoryDTO dto) {
        System.out.print("Количество: ");
        try {
            dto.setQuantity(new BigDecimal(scanner.nextLine()));
        } catch (Exception e) { dto.setQuantity(BigDecimal.ZERO); }

        System.out.print("Цена: ");
        try {
            dto.setPrice(new BigDecimal(scanner.nextLine()));
        } catch (Exception e) { dto.setPrice(BigDecimal.ZERO); }

        dto.setShopId(selectEntity(
                shopService.findAll(),
                ShopDTO::getShopName,
                ShopDTO::getId,
                "Магазин"
        ));

        dto.setProductId(selectEntity(
                productService.findAll(),
                ProductDTO::getProdName,
                ProductDTO::getId,
                "Продукт"
        ));
    }

    private void printInventory(List<InventoryDTO> list) {
        if (list.isEmpty()) { System.out.println("Список пуст."); return; }
        System.out.printf("| %-5s | %-20s | %-20s | %-10s | %-10s |%n", "ID", "Магазин", "Товар", "Кол-во", "Цена");
        System.out.println("-".repeat(80));
        for (InventoryDTO i : list)
            System.out.printf("| %-5d | %-20s | %-20s | %-10s | %-10s |%n",
                    i.getId(), i.getShopName(), i.getProductName(), i.getQuantity(), i.getPrice());
    }

    private <T> long selectEntity(List<T> list, Function<T, String> displayFunc, Function<T, Long> idFunc, String title) {
        if (list.isEmpty()) {
            System.out.println("Нет доступных " + title + ".");
            return 0;
        }

        System.out.println("\n--- Выберите " + title + " ---");
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, displayFunc.apply(list.get(i)));
        }

        System.out.print("Введите номер: ");
        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.isBlank()) return 0;
                int choice = Integer.parseInt(input);

                if (choice == 0) return 0;
                if (choice > 0 && choice <= list.size()) {
                    return idFunc.apply(list.get(choice - 1));
                }
                System.out.print("Неверный номер. Попробуйте еще раз: ");
            } catch (NumberFormatException e) {
                System.out.print("Введите число: ");
            }
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) System.out.println();
    }

    private void waitEnter() {
        System.out.println("\nНажмите Enter, чтобы продолжить...");
        scanner.nextLine();
    }

    private void showMessage(String msg) {
        System.out.println(msg);
        waitEnter();
    }

    private long readLong() {
        try {
            String s = scanner.nextLine();
            if (s.isBlank()) return 0;
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}