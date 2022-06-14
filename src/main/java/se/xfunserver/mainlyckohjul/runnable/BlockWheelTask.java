package se.xfunserver.mainlyckohjul.runnable;


/*
@RequiredArgsConstructor
public class BlockWheelTask extends BukkitRunnable {

    private final WheelManager wheelManager;
    private final Wheel wheel;

    @Setter
    private Player executor;

    private int ticks = 0;
    private int tickDuration = wheel.getRandomSpinDuration();

    @Override
    public void run() {
        if (ticks < tickDuration * 20) {
            // Check if we have won anything

            wheel.getLocation().getWorld().playSound(
                    wheel.getLocation(),
                    Sound.valueOf(LuckyWheel.getCore().getConfig().getString("sounds.finish")),
                    15,
                    1
            );

            cancel();
            return;
        }

        List<WheelSection> sections = wheel.getSections();

        List<Material> backup = sections
                .stream()
                .map(WheelSection::getBlockType)
                .collect(Collectors.toList());

        for (int i = 0; i < sections.size(); i++) {
            sections.get(i).replaceBlocks(
                    backup.get(i == 0
                            ? sections.size() - 1
                            : i - 1));
        }

        wheel.getLocation().getWorld().playSound(
                wheel.getLocation(),
                Sound.valueOf(LuckyWheel.getCore().getConfig().getString("sounds.tick")),
                15,
                1
        );

        ticks += wheel.getTickRate();
    }
}


 */