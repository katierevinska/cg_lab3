import java.awt.*;

public class Algorithms {

    public static void drawLineSbS(Graphics2D g2, int x0, int y0, int x1, int y1, int gridWidth) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        if (dx > dy) {
            int error = dx / 2;
            for (int i = 0; i <= dx; i++) {
                pixel(g2, x0, y0, gridWidth);
                error -= dy;
                if (error < 0) {
                    y0 += sy;
                    error += dx;
                }
                x0 += sx;
            }
        } else {
            int error = dy / 2;
            for (int i = 0; i <= dy; i++) {
                pixel(g2, x0, y0, gridWidth);
                error -= dx;
                if (error < 0) {
                    x0 += sx;
                    error += dy;
                }
                y0 += sy;
            }
        }
    }

    public static void drawLineBr(Graphics2D g2, int x0, int y0, int x1, int y1, int gridWidth) {

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        double err = 0;
        double derr = (dy + 1) / (double) (dx + 1);
        int y = y0;
        int diry = (y1 > y0) ? 1 : -1; // Определяем направление по Y

        // Если x0 больше x1, меняем местами начальные и конечные координаты
        if (x0 > x1) {
            int temp = x0;
            x0 = x1;
            x1 = temp;
            temp = y0;
            y0 = y1;
            y1 = temp; // Также меняем Y
        }

        // Основной цикл для рисования линии
        for (int x = x0; x <= x1; x++) {
            pixel(g2, x, y, gridWidth); // Рисуем текущий пиксель
            err += derr; // Увеличиваем ошибку

            // Если ошибка превышает 1, увеличиваем Y
            while (err >= 1.0) {
                y += diry; // Изменяем Y
                pixel(g2, x, y, gridWidth); // Рисуем новый пиксель
                err -= 1.0; // Уменьшаем ошибку
            }
        }
    }

    public static void ddaAlgorithm(Graphics2D g2, int x0, int y0, int x1, int y1, int gridWidth) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        float x = x0, y = y0;
        for (int i = 0; i <= steps; i++) {
            pixel(g2, Math.round(x), Math.round(y), gridWidth);
            x += xIncrement;
            y += yIncrement;
        }
    }

    public static void bresenhamCircle(Graphics2D g2, int xc, int yc, int r, int gridWidth) {
        int x = 0;
        int y = r;
        int d = 3 - 2 * r;
        drawCirclePoints(g2, xc, yc, x, y, gridWidth);

        while (y >= x) {

            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else
                d = d + 4 * x + 6;

            x++;

            drawCirclePoints(g2, xc, yc, x, y, gridWidth);
        }
    }

    public static void drawCirclePoints(Graphics2D g2, int xc, int yc, int x, int y, int gridWidth) {
        g2.setColor(Color.black);
        g2.fillRect((xc + x) * gridWidth, (yc + y) * gridWidth, gridWidth, gridWidth);
        g2.fillRect((xc - x) * gridWidth, (yc + y) * gridWidth, gridWidth, gridWidth);
        g2.fillRect((xc + x) * gridWidth, (yc - y) * gridWidth, gridWidth, gridWidth);
        g2.fillRect((xc - x) * gridWidth, (yc - y) * gridWidth, gridWidth, gridWidth);
        g2.fillRect((xc + y) * gridWidth, (yc + x) * gridWidth, gridWidth, gridWidth);
        g2.fillRect((xc - y) * gridWidth, (yc + x) * gridWidth, gridWidth, gridWidth);
        g2.fillRect((xc + y) * gridWidth, (yc - x) * gridWidth, gridWidth, gridWidth);
        g2.fillRect((xc - y) * gridWidth, (yc - x) * gridWidth, gridWidth, gridWidth);
    }

    public static void drawWuLine(Graphics2D g2, int x1, int y1, int x2, int y2, int gridWidth) {
        boolean steep = Math.abs(y2 - y1) > Math.abs(x2 - x1);

        if (steep) {
            int temp = x1;
            x1 = y1;
            y1 = temp;
            temp = x2;
            x2 = y2;
            y2 = temp;
        }

        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int dx = x2 - x1;
        int dy = y2 - y1;
        double gradient = (dx == 0) ? 1 : (double) dy / dx;

        double xEnd = x1;
        double yEnd = y1 + gradient * (xEnd - x1);
        int xPixel1 = x1;
        int yPixel1 = (int) Math.floor(yEnd);

        if (steep) {
            drawPixel(g2, yPixel1, xPixel1, 1 - (yEnd - yPixel1), gridWidth);
            drawPixel(g2, yPixel1 + 1, xPixel1, (yEnd - yPixel1), gridWidth);
        } else {
            drawPixel(g2, xPixel1, yPixel1, 1 - (yEnd - yPixel1), gridWidth);
            drawPixel(g2, xPixel1, yPixel1 + 1, (yEnd - yPixel1), gridWidth);
        }

        double intery = yEnd + gradient;

        for (int x = x1 + 1; x < x2; x++) {
            int y = (int) Math.floor(intery);
            if (steep) {
                drawPixel(g2, y, x, 1 - (intery - y), gridWidth);
                drawPixel(g2, y + 1, x, (intery - y), gridWidth);
            } else {
                drawPixel(g2, x, y, 1 - (intery - y), gridWidth);
                drawPixel(g2, x, y + 1, (intery - y), gridWidth);
            }
            intery += gradient;
        }

        // Рисуем последнюю точку
        xEnd = x2;
        yEnd = y2 + gradient * (xEnd - x2);
        int xPixel2 = x2;
        int yPixel2 = (int) Math.floor(yEnd);

        if (steep) {
            drawPixel(g2, yPixel2, xPixel2, 1 - (yEnd - yPixel2), gridWidth);
            drawPixel(g2, yPixel2 + 1, xPixel2, (yEnd - yPixel2), gridWidth);
        } else {
            drawPixel(g2, xPixel2, yPixel2, 1 - (yEnd - yPixel2), gridWidth);
            drawPixel(g2, xPixel2, yPixel2 + 1, (yEnd - yPixel2), gridWidth);
        }
    }

    private static void drawPixel(Graphics2D g2, int x, int y, double intensity, int gridWidth) {
        intensity = Math.max(0, Math.min(1, intensity));
        Color color = new Color(0, 0, 0, (float) intensity);
        g2.setColor(color);
        g2.fillRect(x * gridWidth, y * gridWidth, gridWidth, gridWidth);
    }


    public static void pixel(Graphics2D g2, int x, int y, int gridWidth) {
        g2.setColor(Color.black);
        g2.fillRect(x * gridWidth, y * gridWidth, gridWidth, gridWidth);
    }

}