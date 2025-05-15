package com.anycompany.demo.jumping.controller.misc;

import com.anycompany.demo.jumping.mapper.UserMapper;
import com.anycompany.demo.jumping.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * 头像生成控制器
 * 生成一个包含两个随机大写字母的正方形头像图片
 */
@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private static final Random RANDOM = new Random();
    
    // PNG文件头
    private static final byte[] PNG_HEADER = {
            (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A
    };
    
    // 存储所有字母的位图数据
    private static final Map<Character, boolean[][]> LETTER_BITMAPS = initLetterBitmaps();
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 初始化字母位图数据
     */
    private static Map<Character, boolean[][]> initLetterBitmaps() {
        Map<Character, boolean[][]> bitmaps = new HashMap<>();
        
        // 字母A的位图数据 (7x9像素)
        bitmaps.put('A', new boolean[][] {
            {false, false, true, true, true, false, false},
            {false, true, false, false, false, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, true, true, true, true, true, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true}
        });
        
        // 字母B的位图数据
        bitmaps.put('B', new boolean[][] {
            {true, true, true, true, true, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, true, true, true, true, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, true, true, true, true, true, false}
        });
        
        // 字母C的位图数据
        bitmaps.put('C', new boolean[][] {
            {false, true, true, true, true, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, true},
            {false, true, true, true, true, true, false}
        });
        
        // 字母D的位图数据
        bitmaps.put('D', new boolean[][] {
            {true, true, true, true, true, false, false},
            {true, false, false, false, false, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, true, false},
            {true, true, true, true, true, false, false}
        });
        
        // 字母E的位图数据
        bitmaps.put('E', new boolean[][] {
            {true, true, true, true, true, true, true},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, true, true, true, true, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, true, true, true, true, true, true}
        });
        
        // 字母F的位图数据
        bitmaps.put('F', new boolean[][] {
            {true, true, true, true, true, true, true},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, true, true, true, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false}
        });
        
        // 字母G的位图数据
        bitmaps.put('G', new boolean[][] {
            {false, true, true, true, true, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, true, true, true, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {false, true, true, true, true, true, false}
        });
        
        // 字母H的位图数据
        bitmaps.put('H', new boolean[][] {
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, true, true, true, true, true, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true}
        });
        
        // 字母I的位图数据
        bitmaps.put('I', new boolean[][] {
            {true, true, true, true, true, true, true},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {true, true, true, true, true, true, true}
        });
        
        // 字母J的位图数据
        bitmaps.put('J', new boolean[][] {
            {false, false, false, false, false, false, true},
            {false, false, false, false, false, false, true},
            {false, false, false, false, false, false, true},
            {false, false, false, false, false, false, true},
            {false, false, false, false, false, false, true},
            {false, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {false, true, true, true, true, true, false}
        });
        
        // 字母K的位图数据
        bitmaps.put('K', new boolean[][] {
            {true, false, false, false, false, true, false},
            {true, false, false, false, true, false, false},
            {true, false, false, true, false, false, false},
            {true, false, true, false, false, false, false},
            {true, true, false, false, false, false, false},
            {true, false, true, false, false, false, false},
            {true, false, false, true, false, false, false},
            {true, false, false, false, true, false, false},
            {true, false, false, false, false, true, false}
        });
        
        // 字母L的位图数据
        bitmaps.put('L', new boolean[][] {
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, true, true, true, true, true, true}
        });
        
        // 字母M的位图数据
        bitmaps.put('M', new boolean[][] {
            {true, false, false, false, false, false, true},
            {true, true, false, false, false, true, true},
            {true, false, true, false, true, false, true},
            {true, false, false, true, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true}
        });
        
        // 字母N的位图数据
        bitmaps.put('N', new boolean[][] {
            {true, false, false, false, false, false, true},
            {true, true, false, false, false, false, true},
            {true, false, true, false, false, false, true},
            {true, false, false, true, false, false, true},
            {true, false, false, false, true, false, true},
            {true, false, false, false, false, true, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true}
        });
        
        // 字母O的位图数据
        bitmaps.put('O', new boolean[][] {
            {false, true, true, true, true, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {false, true, true, true, true, true, false}
        });
        
        // 字母P的位图数据
        bitmaps.put('P', new boolean[][] {
            {true, true, true, true, true, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, true, true, true, true, true, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false}
        });
        
        // 字母Q的位图数据
        bitmaps.put('Q', new boolean[][] {
            {false, true, true, true, true, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, true, false, true},
            {true, false, false, false, false, true, false},
            {false, true, true, true, true, false, true}
        });
        
        // 字母R的位图数据
        bitmaps.put('R', new boolean[][] {
            {true, true, true, true, true, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, true, true, true, true, true, false},
            {true, false, true, false, false, false, false},
            {true, false, false, true, false, false, false},
            {true, false, false, false, true, false, false},
            {true, false, false, false, false, true, false}
        });
        
        // 字母S的位图数据
        bitmaps.put('S', new boolean[][] {
            {false, true, true, true, true, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {false, true, true, true, true, true, false},
            {false, false, false, false, false, false, true},
            {false, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {false, true, true, true, true, true, false}
        });
        
        // 字母T的位图数据
        bitmaps.put('T', new boolean[][] {
            {true, true, true, true, true, true, true},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false}
        });
        
        // 字母U的位图数据
        bitmaps.put('U', new boolean[][] {
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {false, true, true, true, true, true, false}
        });
        
        // 字母V的位图数据
        bitmaps.put('V', new boolean[][] {
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {false, true, false, false, false, true, false},
            {false, false, true, false, true, false, false},
            {false, false, false, true, false, false, false}
        });
        
        // 字母W的位图数据
        bitmaps.put('W', new boolean[][] {
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true},
            {true, false, false, true, false, false, true},
            {true, false, false, true, false, false, true},
            {true, false, true, false, true, false, true},
            {true, true, false, false, false, true, true},
            {true, false, false, false, false, false, true}
        });
        
        // 字母X的位图数据
        bitmaps.put('X', new boolean[][] {
            {true, false, false, false, false, false, true},
            {false, true, false, false, false, true, false},
            {false, false, true, false, true, false, false},
            {false, false, false, true, false, false, false},
            {false, false, false, true, false, false, false},
            {false, false, true, false, true, false, false},
            {false, true, false, false, false, true, false},
            {true, false, false, false, false, false, true},
            {true, false, false, false, false, false, true}
        });
        
        // 字母Y的位图数据
        bitmaps.put('Y', new boolean[][] {
            {true, false, false, false, false, false, true},
            {false, true, false, false, false, true, false},
            {false, false, true, false, true, false, false},
            {false, false, false, true, false, false, false},
            {false, false, false, true, false, false, false},
            {false, false, false, true, false, false, false},
            {false, false, false, true, false, false, false},
            {false, false, false, true, false, false, false},
            {false, false, false, true, false, false, false}
        });
        
        // 字母Z的位图数据
        bitmaps.put('Z', new boolean[][] {
            {true, true, true, true, true, true, true},
            {false, false, false, false, false, true, false},
            {false, false, false, false, true, false, false},
            {false, false, false, true, false, false, false},
            {false, false, true, false, false, false, false},
            {false, true, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, false, false, false, false, false, false},
            {true, true, true, true, true, true, true}
        });
        
        return bitmaps;
    }

    /**
     * 生成一个简单的头像图片
     * @param size 图像尺寸，默认为200像素
     * @param bgColor 背景颜色，格式为'r,g,b'，默认随机生成
     * @param letter1 第一个字母，为空则随机生成
     * @param letter2 第二个字母，为空则随机生成
     * @return 头像图片
     */
    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateAvatar(
            @RequestParam(value = "size", defaultValue = "200") int size,
            @RequestParam(value = "bgColor", required = false) String bgColor,
            @RequestParam(value = "letter1", required = false) String letter1,
            @RequestParam(value = "letter2", required = false) String letter2) {

        try {
            // 解析字母参数
            Character l1 = null;
            Character l2 = null;
            
            if (letter1 != null && !letter1.isEmpty() && letter1.length() == 1) {
                char c = Character.toUpperCase(letter1.charAt(0));
                if (c >= 'A' && c <= 'Z') {
                    l1 = c;
                }
            }
            
            if (letter2 != null && !letter2.isEmpty() && letter2.length() == 1) {
                char c = Character.toUpperCase(letter2.charAt(0));
                if (c >= 'A' && c <= 'Z') {
                    l2 = c;
                }
            }
            
            // 确保不生成相同的字母
            if (l1 != null && l2 != null && l1.equals(l2)) {
                l2 = (char) (((l2 - 'A' + 1) % 26) + 'A'); // 取下一个字母
            }
            
            // 生成头像
            byte[] imageBytes = generateSimpleAvatar(size, bgColor, l1, l2);
            
            // 设置HTTP头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageBytes.length);
            
            // 返回响应
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 根据用户ID生成头像
     * 使用用户名的前两个字符作为头像上的字母
     * 
     * @param id 用户ID
     * @param size 头像大小，默认200像素
     * @param bgColor 背景颜色，格式为"R,G,B"
     * @return 头像图片
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<byte[]> generateAvatarByUserId(
            @PathVariable("id") Long id,
            @RequestParam(value = "size", defaultValue = "200") int size) {

        try {
            // 从注入的UserMapper中获取用户
            User user = userMapper.getById(id);
            
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            
            // 从邮箱中提取字母
            String email = user.getEmail();
            if (email == null || email.isEmpty()) {
                // 如果没有邮箱，使用用户名
                email = user.getUsername();
            }
            
            Character l1 = null;
            Character l2 = null;
            
            if (email != null && !email.isEmpty()) {
                if (email.length() >= 1) {
                    char c = email.charAt(0);
                    if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || Character.isDigit(c)) {
                        l1 = Character.toUpperCase(c);
                    } else {
                        // 非字母数字，使用默认字母
                        l1 = 'U';
                    }
                }
                
                if (email.length() >= 2) {
                    char c = email.charAt(1);
                    if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || Character.isDigit(c)) {
                        l2 = Character.toUpperCase(c);
                    } else {
                        // 非字母数字，使用默认字母
                        l2 = 'N';
                    }
                } else {
                    // 邮箱只有一个字符，复制第一个
                    l2 = l1;
                }
            } else {
                // 使用默认字母
                l1 = 'U';
                l2 = 'N';
            }
            
            // 根据邮箱哈希选择背景颜色
            String bgColor = getBackgroundColorFromEmail(email);
            
            // 生成头像
            byte[] imageBytes = generateSimpleAvatar(size, bgColor, l1, l2);
            
            // 设置HTTP头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageBytes.length);
            
            // 返回响应
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 根据邮箱生成背景颜色
     * @param email 用户邮箱
     * @return 背景颜色，格式为"R,G,B"
     */
    private String getBackgroundColorFromEmail(String email) {
        // 预设的几种背景颜色
        final String[] PREDEFINED_COLORS = {
            "41,128,185",  // 蓝色
            "39,174,96",   // 绿色
            "142,68,173",  // 紫色
            "211,84,0",    // 橙色
            "22,160,133",  // 松石绿
            "192,57,43",   // 红色
            "243,156,18",  // 黄色
            "52,73,94"     // 深蓝灰
        };
        
        if (email == null || email.isEmpty()) {
            // 默认颜色
            return PREDEFINED_COLORS[0];
        }
        
        // 计算哈希值
        int hash = email.hashCode();
        if (hash < 0) {
            hash = -hash; // 确保为正数
        }
        
        // 选择颜色
        return PREDEFINED_COLORS[hash % PREDEFINED_COLORS.length];
    }
    
    /**
     * 生成一个简单的纯色头像，中心有两个大写字母（可指定或随机）
     */
    private byte[] generateSimpleAvatar(int size, String colorStr, Character letter1Param, Character letter2Param) throws IOException {
        // 决定字母
        char letter1 = (letter1Param != null) ? letter1Param : (char) ('A' + RANDOM.nextInt(26));
        char letter2;
        
        if (letter2Param != null) {
            letter2 = letter2Param;
        } else {
            // 随机生成第二个字母，确保与第一个不同
            do {
                letter2 = (char) ('A' + RANDOM.nextInt(26));
            } while (letter2 == letter1);
        }
        
        // 确保两个字母不同，如果用户传入的两个字母相同，则修改第二个
        if (letter1 == letter2) {
            letter2 = (char) (((letter2 - 'A' + 1) % 26) + 'A'); // 取下一个字母
        }
        
        // 解析或生成颜色
        int[] rgb = parseColorStr(colorStr);
        if (rgb == null) {
            rgb = new int[] {
                    RANDOM.nextInt(256),
                    RANDOM.nextInt(256),
                    RANDOM.nextInt(256)
            };
        }
        
        // 计算文字颜色（背景色的反色，确保可见性）
        int[] textRgb = new int[] {
            255 - rgb[0],
            255 - rgb[1],
            255 - rgb[2]
        };
        
        // 确保尺寸合理
        size = Math.max(40, Math.min(1000, size)); // 限制在40到1000像素之间
        
        // 创建输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // 写入PNG文件头
        baos.write(PNG_HEADER);
        
        // 写入IHDR数据块（图像头信息）
        byte[] ihdrData = createIHDRChunk(size, size);
        baos.write(ihdrData);
        
        // 写入IDAT数据块（图像数据）
        byte[] idatData = createIDATChunk(size, rgb, textRgb, letter1, letter2);
        baos.write(idatData);
        
        // 写入IEND数据块（文件结束标记）
        byte[] iendData = createIENDChunk();
        baos.write(iendData);
        
        return baos.toByteArray();
    }
    
    /**
     * 创建IHDR数据块
     */
    private byte[] createIHDRChunk(int width, int height) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // 数据块长度（固定为13字节）
        writeInt(baos, 13);
        
        // 数据块类型
        baos.write(new byte[] {'I', 'H', 'D', 'R'});
        
        // 开始计算CRC
        CRC32 crc = new CRC32();
        crc.update(new byte[] {'I', 'H', 'D', 'R'});
        
        // 图像宽度
        byte[] widthBytes = intToBytes(width);
        baos.write(widthBytes);
        crc.update(widthBytes);
        
        // 图像高度
        byte[] heightBytes = intToBytes(height);
        baos.write(heightBytes);
        crc.update(heightBytes);
        
        // 位深度(8)，颜色类型(2-RGB)，压缩方法(0)，过滤方法(0)，交错方法(0)
        byte[] params = {8, 2, 0, 0, 0};
        baos.write(params);
        crc.update(params);
        
        // CRC校验
        writeInt(baos, (int) crc.getValue());
        
        return baos.toByteArray();
    }
    
    /**
     * 创建IDAT数据块（图像数据）
     */
    private byte[] createIDATChunk(int size, int[] rgb, int[] textRgb, char letter1, char letter2) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // 先创建图像数据
        ByteArrayOutputStream imageData = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterStream = new DeflaterOutputStream(imageData, new Deflater(Deflater.BEST_COMPRESSION));
        
        // 获取位图数据
        boolean[][] bitmap1 = LETTER_BITMAPS.get(letter1);
        boolean[][] bitmap2 = LETTER_BITMAPS.get(letter2);
        
        // 字母大小（根据图像尺寸缩放，增大字体）
        int letterSize = Math.max(size / 5, 20); // 字母大小至少20像素，原来是size/10
        int letterWidth = bitmap1[0].length * letterSize / 7;
        int letterHeight = bitmap1.length * letterSize / 9;
        
        // 计算两个字母的位置
        int spacing = letterWidth / 4; // 字母间距
        int totalWidth = letterWidth * 2 + spacing; // 两个字母加间距的总宽度
        
        // 第一个字母的起始位置（居中）
        int startX1 = (size - totalWidth) / 2;
        int startY = (size - letterHeight) / 2;
        
        // 第二个字母的起始位置
        int startX2 = startX1 + letterWidth + spacing;
        
        // 对于每一行，写入过滤类型字节(0)，然后是RGB数据
        for (int y = 0; y < size; y++) {
            byte[] rowData = new byte[size * 3 + 1];
            rowData[0] = 0; // 过滤类型
            
            for (int x = 0; x < size; x++) {
                // 确定当前像素是在哪个字母的范围内
                boolean isLetterPixel = false;
                
                // 检查第一个字母
                if (x >= startX1 && x < startX1 + letterWidth && 
                    y >= startY && y < startY + letterHeight) {
                    
                    // 计算字母内的相对位置
                    int letterX = (x - startX1) * 7 / letterWidth;
                    int letterY = (y - startY) * 9 / letterHeight;
                    
                    // 确保索引不越界
                    letterX = Math.min(6, Math.max(0, letterX));
                    letterY = Math.min(8, Math.max(0, letterY));
                    
                    isLetterPixel = bitmap1[letterY][letterX];
                }
                
                // 检查第二个字母
                if (!isLetterPixel && x >= startX2 && x < startX2 + letterWidth && 
                    y >= startY && y < startY + letterHeight) {
                    
                    // 计算字母内的相对位置
                    int letterX = (x - startX2) * 7 / letterWidth;
                    int letterY = (y - startY) * 9 / letterHeight;
                    
                    // 确保索引不越界
                    letterX = Math.min(6, Math.max(0, letterX));
                    letterY = Math.min(8, Math.max(0, letterY));
                    
                    isLetterPixel = bitmap2[letterY][letterX];
                }
                
                int offset = 1 + x * 3;
                if (isLetterPixel) {
                    // 使用文字颜色
                    rowData[offset] = (byte) textRgb[0];     // R
                    rowData[offset + 1] = (byte) textRgb[1]; // G
                    rowData[offset + 2] = (byte) textRgb[2]; // B
                } else {
                    // 使用背景颜色
                    rowData[offset] = (byte) rgb[0];     // R
                    rowData[offset + 1] = (byte) rgb[1]; // G
                    rowData[offset + 2] = (byte) rgb[2]; // B
                }
            }
            
            deflaterStream.write(rowData);
        }
        
        deflaterStream.finish();
        deflaterStream.close();
        
        // 获取压缩后的图像数据
        byte[] compressedData = imageData.toByteArray();
        
        // 数据块长度
        writeInt(baos, compressedData.length);
        
        // 数据块类型
        byte[] chunkType = {'I', 'D', 'A', 'T'};
        baos.write(chunkType);
        
        // 计算CRC
        CRC32 crc = new CRC32();
        crc.update(chunkType);
        
        // 写入压缩数据
        baos.write(compressedData);
        crc.update(compressedData);
        
        // CRC校验
        writeInt(baos, (int) crc.getValue());
        
        return baos.toByteArray();
    }
    
    /**
     * 创建IEND数据块（文件结束标记）
     */
    private byte[] createIENDChunk() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        // 数据块长度（0）
        writeInt(baos, 0);
        
        // 数据块类型
        byte[] chunkType = {'I', 'E', 'N', 'D'};
        baos.write(chunkType);
        
        // CRC校验
        CRC32 crc = new CRC32();
        crc.update(chunkType);
        writeInt(baos, (int) crc.getValue());
        
        return baos.toByteArray();
    }
    
    /**
     * 解析颜色字符串，格式为"r,g,b"
     */
    private int[] parseColorStr(String colorStr) {
        if (colorStr == null || colorStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            String[] parts = colorStr.split(",");
            if (parts.length != 3) {
                return null;
            }
            
            int r = Integer.parseInt(parts[0].trim());
            int g = Integer.parseInt(parts[1].trim());
            int b = Integer.parseInt(parts[2].trim());
            
            // 验证RGB值范围
            r = Math.min(255, Math.max(0, r));
            g = Math.min(255, Math.max(0, g));
            b = Math.min(255, Math.max(0, b));
            
            return new int[] {r, g, b};
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 写入32位整数（大端序）
     */
    private void writeInt(ByteArrayOutputStream baos, int value) throws IOException {
        baos.write(intToBytes(value));
    }
    
    /**
     * 将int转换为字节数组（大端序）
     */
    private byte[] intToBytes(int value) {
        return new byte[] {
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }
} 