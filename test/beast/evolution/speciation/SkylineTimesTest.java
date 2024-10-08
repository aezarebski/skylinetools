package beast.evolution.speciation;

import beast.base.inference.parameter.BooleanParameter;
import beast.base.inference.parameter.RealParameter;
import bdsky.evolution.speciation.BirthDeathSkylineModelTestHelper;
import beast.base.evolution.tree.Tree;
import beast.base.evolution.tree.TreeParser;
import org.junit.Test;
import junit.framework.TestCase;

import java.util.List;

/**
 * This test is to check how bdsky (bdsky.evolution.speciation.BirthDeathSkylineModel) sets change-point times, depending
 * on the input.
 *
 * There are 5 different ways to set the change-point times:
 *
 * 1.) Equally spaced between origin and present
 *
 * 2.) Time from the origin to the present:
 *  a) Absolute times (in the same units as the tree branches)
 *  b) Relative times (between 0 and 1)
 *
 * 3.) Time from the present to the origin (reverse-time):
 *  a) Absolute times (in the same units as the tree branches)
 *  b) Relative times (between 0 and 1)
 *
 *  The bdsky package itself never tests that this actually works in the expected way.
 *
 *
 * Created by dlouis on 06/04/16.
 */
public class SkylineTimesTest extends TestCase {


    /*
     * Tree used simulated by:
     *
     * library(TreeSim)
     * set.seed(1)
     * tree   <- sim.bd.taxa(n=10, numbsim=1, lambda=2, mu=0.5, frac=1, complete=FALSE, stochsampling=TRUE)
     *
     * Tree MRCA = 2.55557351
     *
     */


    /**
     * Expect (1, 2, 3, 4, 5.0)
     *
     *
     * @throws Exception
     */
    @Test
    public void testSkylineTimesEquallySpaced() throws Exception {

        Tree tree = new TreeParser();

        tree.initByName("newick", "(((((t8:0.7222085766,(t5:0.04762441103,t1:0.6054274304):0.6506888754):0.5517904073,(t2:0.514380477,t10:0.4632931469):0.5324084692):0.3951336571,t6:0.7816201891):0.3005419225,t4:0.1858745544):0.05199122007,((t7:0.1588048418,t9:0.03676356945):0.5750080554,t3:1.336592522):0.02329921031):0.2517272777;",
                "IsLabelledNewick", true, "adjustTipHeights", false);

        BirthDeathSkylineModelTestHelper bdsky = new BirthDeathSkylineModelTestHelper();

        RealParameter origin = new RealParameter("5");
        RealParameter Re     = new RealParameter("1 1 1 1 1");
        RealParameter delta  = new RealParameter("1 1 1 1 1");
        RealParameter s      = new RealParameter("1 1 1 1 1");
        BooleanParameter reverse = new BooleanParameter("true true true true true");


        bdsky.initByName("tree", tree, "origin", origin,
                "reproductiveNumber", Re, "becomeUninfectiousRate", delta, "samplingProportion", s, "reverseTimeArrays", reverse);

        List<Double> birthRateTimes = bdsky.getBirthRateChangeTimes();
        double [] expected = new double [] {1, 2, 3, 4, 5.0};
        int i = 0;
        for (Double t : birthRateTimes) {
            //System.out.println(t);
            assertEquals(t, expected[i]);
            i++;
        }

        //double logP = bdsky.calculateTreeLogLikelihood(tree);
    }


    /**
     * Expect (1.0, 2.0, 3.0, 4.0, 5.0)
     *
     *
     * @throws Exception
     */
    @Test
    public void testSkylineTimesRelative() throws Exception {

        Tree tree = new TreeParser();

        tree.initByName("newick", "(((((t8:0.7222085766,(t5:0.04762441103,t1:0.6054274304):0.6506888754):0.5517904073,(t2:0.514380477,t10:0.4632931469):0.5324084692):0.3951336571,t6:0.7816201891):0.3005419225,t4:0.1858745544):0.05199122007,((t7:0.1588048418,t9:0.03676356945):0.5750080554,t3:1.336592522):0.02329921031):0.2517272777;",
                "IsLabelledNewick", true, "adjustTipHeights", false);

        BirthDeathSkylineModelTestHelper bdsky = new BirthDeathSkylineModelTestHelper();

        RealParameter origin = new RealParameter("5");
        RealParameter Re     = new RealParameter("1 1 1 1 1");
        RealParameter delta  = new RealParameter("1 1 1 1 1");
        RealParameter s      = new RealParameter("1 1 1 1 1");
        RealParameter birthtimes = new RealParameter("0.0 0.2 0.4 0.6 0.8");
        BooleanParameter reverse = new BooleanParameter("false false false false false");

        bdsky.initByName("tree", tree, "origin", origin,
                         "reproductiveNumber", Re, "becomeUninfectiousRate", delta, "samplingProportion", s,
                         "birthRateTimesRelative", true, "birthRateChangeTimes", birthtimes,
                         "reverseTimeArrays", reverse);

        List<Double> birthRateTimes = bdsky.getBirthRateChangeTimes();
        double [] expected = new double [] {1.0, 2.0, 3.0, 4.0, 5.0};
        int i = 0;
        for (Double t : birthRateTimes) {
            //System.out.println(t);
            assertEquals(t, expected[i]);
            i++;
        }

        //double logP = bdsky.calculateTreeLogLikelihood(tree);
    }

    /**
     * Expect (0.2, 0.4, 0.6, 0.8, 5.0)
     *
     *
     * @throws Exception
     */
    @Test
    public void testSkylineTimes() throws Exception {

        Tree tree = new TreeParser();

        tree.initByName("newick", "(((((t8:0.7222085766,(t5:0.04762441103,t1:0.6054274304):0.6506888754):0.5517904073,(t2:0.514380477,t10:0.4632931469):0.5324084692):0.3951336571,t6:0.7816201891):0.3005419225,t4:0.1858745544):0.05199122007,((t7:0.1588048418,t9:0.03676356945):0.5750080554,t3:1.336592522):0.02329921031):0.2517272777;",
                "IsLabelledNewick", true, "adjustTipHeights", false);

        BirthDeathSkylineModelTestHelper bdsky = new BirthDeathSkylineModelTestHelper();

        RealParameter origin = new RealParameter("5");
        RealParameter Re     = new RealParameter("1 1 1 1 1");
        RealParameter delta  = new RealParameter("1 1 1 1 1");
        RealParameter s      = new RealParameter("1 1 1 1 1");
        RealParameter birthtimes = new RealParameter("0 0.2 0.4 0.6 0.8");
        BooleanParameter reverse = new BooleanParameter("false false false false false");


        bdsky.initByName("tree", tree, "origin", origin,
                "reproductiveNumber", Re, "becomeUninfectiousRate", delta, "samplingProportion", s,
                "birthRateTimesRelative", false, "birthRateChangeTimes", birthtimes,
                "reverseTimeArrays", reverse);

        List<Double> birthRateTimes = bdsky.getBirthRateChangeTimes();
        double [] expected = new double [] {0.2, 0.4, 0.6, 0.8, 5.0};
        int i = 0;
        for (Double t : birthRateTimes) {
            //System.out.println(t);
            assertEquals(t, expected[i]);
            i++;
        }

    }


    /**
     * Expect (1.0, 2.0, 3.0, 4.0, 5.0)
     *
     *
     * @throws Exception
     */
    @Test
    public void testSkylineTimesRelativeReverse() throws Exception {

        Tree tree = new TreeParser();

        tree.initByName("newick", "(((((t8:0.7222085766,(t5:0.04762441103,t1:0.6054274304):0.6506888754):0.5517904073,(t2:0.514380477,t10:0.4632931469):0.5324084692):0.3951336571,t6:0.7816201891):0.3005419225,t4:0.1858745544):0.05199122007,((t7:0.1588048418,t9:0.03676356945):0.5750080554,t3:1.336592522):0.02329921031):0.2517272777;",
                "IsLabelledNewick", true, "adjustTipHeights", false);

        BirthDeathSkylineModelTestHelper bdsky = new BirthDeathSkylineModelTestHelper();

        RealParameter origin = new RealParameter("5");
        RealParameter Re     = new RealParameter("1 1 1 1 1");
        RealParameter delta  = new RealParameter("1 1 1 1 1");
        RealParameter s      = new RealParameter("1 1 1 1 1");
        RealParameter birthtimes = new RealParameter("0 0.2 0.4 0.6 0.8");
        BooleanParameter reverse = new BooleanParameter("true true true true true");


        bdsky.initByName("tree", tree, "origin", origin,
                "reproductiveNumber", Re, "becomeUninfectiousRate", delta, "samplingProportion", s,
                "birthRateTimesRelative", true, "birthRateChangeTimes", birthtimes,
                "reverseTimeArrays", reverse);

        List<Double> birthRateTimes = bdsky.getBirthRateChangeTimes();
        double [] expected = new double [] {1.0, 2.0, 3.0, 4.0, 5.0};
        int i = 0;
        for (Double t : birthRateTimes) {
            //System.out.println(t);
            assertEquals(t, expected[i], 1E-4);
            i++;
        }

    }


    /**
     * Expect (4.2, 4.4, 4.6, 4.8, 5.0)
     *
     *
     * @throws Exception
     */
    @Test
    public void testSkylineTimesReverse() throws Exception {

        Tree tree = new TreeParser();

        tree.initByName("newick", "(((((t8:0.7222085766,(t5:0.04762441103,t1:0.6054274304):0.6506888754):0.5517904073,(t2:0.514380477,t10:0.4632931469):0.5324084692):0.3951336571,t6:0.7816201891):0.3005419225,t4:0.1858745544):0.05199122007,((t7:0.1588048418,t9:0.03676356945):0.5750080554,t3:1.336592522):0.02329921031):0.2517272777;",
                "IsLabelledNewick", true, "adjustTipHeights", false);

        BirthDeathSkylineModelTestHelper bdsky = new BirthDeathSkylineModelTestHelper();

        RealParameter origin = new RealParameter("5");
        RealParameter Re     = new RealParameter("1 1 1 1 1");
        RealParameter delta  = new RealParameter("1 1 1 1 1");
        RealParameter s      = new RealParameter("1 1 1 1 1");
        RealParameter birthtimes = new RealParameter("0 0.2 0.4 0.6 0.8");
        BooleanParameter reverse = new BooleanParameter("true true true true true");


        bdsky.initByName("tree", tree, "origin", origin,
                "reproductiveNumber", Re, "becomeUninfectiousRate", delta, "samplingProportion", s,
                "birthRateTimesRelative", false, "birthRateChangeTimes", birthtimes,
                "reverseTimeArrays", reverse);

        List<Double> birthRateTimes = bdsky.getBirthRateChangeTimes();
        double [] expected = new double [] {4.2, 4.4, 4.6, 4.8, 5.0};
        int i = 0;
        for (Double t : birthRateTimes) {
            System.out.println(t);
            assertEquals(t, expected[i]);
            i++;
        }

        //double logP = bdsky.calculateTreeLogLikelihood(tree);
    }










}
