INSERT INTO board (id, title, opened_count, closed_count)
VALUES
(1, 'Shopping List', 4, 1),
(2, 'TO DO List', 2, 1)
;

INSERT INTO category (id, title, color)
VALUES
(1, 'Milk Products', '#4395EC'),
(2, 'Bakery', '#DB5F29'),
(3, 'Fruits', '#388A0A'),
(4, 'Meat', '#8A0E04'),
(5, 'Test category', '#C70039'),
(6, 'Task category', '#733E71')
;

INSERT INTO task (id, title, description, amount, completed, created_date, completed_date, board_id, category_id)
VALUES
(1, 'Milk', NULL, 1, FALSE, '2020-04-27 15:27:29', NULL, 1, 1),
(2, 'Bread', NULL, 1, FALSE, '2020-04-27 15:27:30', NULL, 1, 2),
(3, 'Apples', NULL, 2, FALSE, '2020-04-27 15:28:29', NULL, 1, 3),
(4, 'Grapes', NULL, 1, TRUE, '2020-04-27 15:28:39', '2020-04-28 10:29:29', 1, 3),
(5, 'Chicken fillet', NULL, 4, FALSE, '2020-04-27 15:29:29', NULL, 1, 4),
(6, 'Add test', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ac venenatis nulla, eu convallis metus. Sed consectetur ultricies tempor. Suspendisse eu dolor molestie, lobortis orci sit amet, lacinia odio.', NULL, FALSE, '2020-05-05 10:37:29', NULL, 2, 5),
(7, 'Finish part 1', NULL, NULL, TRUE, '2020-05-08 16:08:29', '2020-05-18 14:00:20', 2, 6),
(8, 'Finish part 2', NULL, NULL, FALSE, '2020-05-18 15:00:20', NULL, 2, 6)
;

INSERT INTO stat (id, opened_total, closed_total)
VALUES
(1, 6, 2)
;