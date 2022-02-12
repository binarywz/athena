package binary.wz.client;

import binary.wz.proto.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class ClientHandler extends SimpleChannelInboundHandler<DataInfo.Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Message msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int randomInt = new Random().nextInt(3);

        DataInfo.Message message = null;

        if (0 == randomInt) {
            message = DataInfo.Message.newBuilder().
                    setDataType(DataInfo.Message.DataType.PersonType).setPerson(DataInfo.Person.newBuilder().
                    setName("James").setAge(20).setAddress("America").build()).build();
        } else if (1 == randomInt) {
            message = DataInfo.Message.newBuilder().
                    setDataType(DataInfo.Message.DataType.DogType).setDog(DataInfo.Dog.newBuilder().
                    setName("Kobe").setAge(25).build()).build();
        } else {
            message = DataInfo.Message.newBuilder().
                    setDataType(DataInfo.Message.DataType.CatType).setCat(DataInfo.Cat.newBuilder().
                    setName("Jordan").setCity("Beijing").build()).build();
        }

        ctx.writeAndFlush(message);
    }
}
